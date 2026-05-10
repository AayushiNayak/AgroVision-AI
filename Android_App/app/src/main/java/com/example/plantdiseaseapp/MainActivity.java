package com.example.plantdiseaseapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.plantdiseaseapp.databinding.ActivityMainBinding;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private Interpreter tflite;
    private List<String> labelList = new ArrayList<>();
    private Map<String, String> recommendations;
    private Uri cameraImageUri;
    private String statusMessage = "Ready";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            initRecommendations();
            loadAssets();
            
            binding.resultText.setText(statusMessage);

            setupClickListeners();
        } catch (Exception e) {
            Log.e(TAG, "Fatal Error", e);
        }
    }

    private void loadAssets() {
        // Load Labels
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")))) {
            labelList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) labelList.add(line.trim());
            }
            if (labelList.isEmpty()) {
                statusMessage = "Error: labels.txt is empty";
                return;
            }
        } catch (IOException e) {
            statusMessage = "Error: labels.txt missing";
            return;
        }

        // Load Model
        try {
            MappedByteBuffer tfliteModel = FileUtil.loadMappedFile(this, "plant_care_offline.tflite");
            Interpreter.Options options = new Interpreter.Options();
            // Just including the select-tf-ops dependency is enough for Flex Ops support.
            options.setAllowBufferHandleOutput(true);
            
            tflite = new Interpreter(tfliteModel, options);
            statusMessage = "System Ready. Select an image.";
        } catch (Exception e) {
            statusMessage = "Model Error: " + e.getMessage();
            Log.e(TAG, "Model fail", e);
        }
    }

    private void setupClickListeners() {
        binding.btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        binding.btnCaptureImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });
    }

    private void runInference(Bitmap bitmap) {
        if (tflite == null) {
            binding.resultText.setText(statusMessage);
            return;
        }

        try {
            Bitmap argbBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            
            ImageProcessor imageProcessor = new ImageProcessor.Builder()
                    .add(new ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
                    .add(new NormalizeOp(0.0f, 255.0f))
                    .build();

            TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
            tensorImage.load(argbBitmap);
            tensorImage = imageProcessor.process(tensorImage);

            int[] outShape = tflite.getOutputTensor(0).shape();
            TensorBuffer outputBuffer = TensorBuffer.createFixedSize(outShape, DataType.FLOAT32);
            
            tflite.run(tensorImage.getBuffer(), outputBuffer.getBuffer());
            float[] results = outputBuffer.getFloatArray();

            int maxIdx = 0;
            for (int i = 0; i < results.length; i++) {
                if (results[i] > results[maxIdx]) maxIdx = i;
            }

            if (maxIdx < labelList.size() && results[maxIdx] > 0.05f) {
                String label = labelList.get(maxIdx);
                String cleanName = label.replace("_", " ");
                binding.resultText.setText(String.format(Locale.getDefault(), "%s (%.1f%%)", cleanName, results[maxIdx] * 100));
                binding.recommendationText.setText(recommendations.getOrDefault(label, "Treatment: Contact your local agricultural center."));
            } else {
                binding.resultText.setText("Plant not recognized");
                binding.recommendationText.setText("Try taking a closer photo with better lighting.");
            }
        } catch (Exception e) {
            binding.resultText.setText("Analysis Error: " + e.getMessage());
        }
    }

    private void processImage(Uri uri) {
        try {
            Bitmap bitmap;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.getContentResolver(), uri));
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            }
            binding.imageView.setImageBitmap(bitmap);
            binding.resultText.setText("Analyzing...");
            runInference(bitmap);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) launchCamera();
            });

    private final ActivityResultLauncher<Uri> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), success -> {
                if (success && cameraImageUri != null) processImage(cameraImageUri);
            });

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    processImage(result.getData().getData());
                }
            });

    private void launchCamera() {
        try {
            cameraImageUri = createImageFileUri();
            if (cameraImageUri != null) takePictureLauncher.launch(cameraImageUri);
        } catch (IOException e) {
            Log.e(TAG, "Camera launch fail", e);
        }
    }

    private Uri createImageFileUri() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile("SCAN_" + timeStamp + "_", ".jpg", storageDir);
        return FileProvider.getUriForFile(this, getPackageName() + ".provider", image);
    }

    private void initRecommendations() {
        recommendations = new HashMap<>();
        recommendations.put("Potato_Early_blight", "Treatment: Apply Chlorothalonil or Mancozeb fungicides; improve air circulation.");
        recommendations.put("Rice_Brown_spot", "Treatment: Increase Potassium fertilization; ensure good water management.");
        recommendations.put("Cauliflower_Healthy", "Plant is healthy. Keep soil consistently moist.");
        recommendations.put("Tomato_Late_blight", "Treatment: Remove infected foliage; use Copper-based sprays.");
        recommendations.put("Tomato_healthy", "Tomato plant is healthy! Provide support stakes.");
        recommendations.put("Tomato_Bacterial_spot", "Treatment: Use copper fungicides; avoid overhead watering.");
        recommendations.put("Rice_Bacterial_leaf_blight", "Treatment: Apply stable bleaching powder; use resistant seeds.");
    }

    @Override
    protected void onDestroy() {
        if (tflite != null) tflite.close();
        super.onDestroy();
    }
}

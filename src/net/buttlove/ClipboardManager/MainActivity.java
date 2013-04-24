package net.buttlove.ClipboardManager;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private ClipboardManager clipboardManager;
    private EditText clipboardContent;
    private Button saveClipboardButton;
    private Button clearClipboardButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Load widgets into fields
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clearClipboardButton = (Button) findViewById(R.id.clearClipboardButton);
        saveClipboardButton = (Button) findViewById(R.id.saveClipboardButton);
        clipboardContent = (EditText) findViewById(R.id.clipboardContent);

        // Setup event listeners
        clipboardManager.addPrimaryClipChangedListener(new UpdateTextfieldOnClipboardChangedListener());
        clearClipboardButton.setOnClickListener(new ClearClipboardOnClickListener());
        saveClipboardButton.setOnClickListener(new SaveClipboardOnClickListener());

        updateClipboardContent();
    }

    private void updateClipboardContent() {
        clipboardContent.setText(clipboardManager.getPrimaryClip().getItemAt(0).coerceToText(getApplicationContext()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateClipboardContent();
    }

    private class UpdateTextfieldOnClipboardChangedListener
        implements ClipboardManager.OnPrimaryClipChangedListener {
        @Override
        public void onPrimaryClipChanged() {
            updateClipboardContent();
        }

    }

    private class ClearClipboardOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("", ""));
        }
    }

    private class SaveClipboardOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("", clipboardContent.getText()));
        }
    }
}

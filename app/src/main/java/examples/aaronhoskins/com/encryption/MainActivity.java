package examples.aaronhoskins.com.encryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.security.KeyPair;

public class MainActivity extends AppCompatActivity {
    public static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    TextView tvEncryptedString;
    TextView tvDecryptedString;
    EditText etStringToEncrypt;
    KeyStoreWrapper keyStoreWrapper;
    CipherWrapper cipherWrapper;
    KeyPair keyPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind views
        tvEncryptedString = findViewById(R.id.tvEncryptedString);
        tvDecryptedString = findViewById(R.id.tvDecryptedString);
        etStringToEncrypt = findViewById(R.id.etUserInputString);

        //instantiate wrappers
        try{
            keyStoreWrapper = new KeyStoreWrapper(getApplicationContext());
            cipherWrapper = new CipherWrapper(TRANSFORMATION);

            //create public/private key
            keyStoreWrapper.createKeyPair("master");
            keyPair = keyStoreWrapper.getAsymKey("master");
        } catch(Exception e) {
            Log.e("TAG", "onCreate: ", e);
        }


    }

    public void onClick(View view) {
        String userInput = etStringToEncrypt.getText().toString();
        try {
            String encryptedString = cipherWrapper.encrypt(userInput, keyPair.getPublic());
            String decryptedString = cipherWrapper.decrypt(encryptedString, keyPair.getPrivate());
            tvEncryptedString.setText(encryptedString);
            tvDecryptedString.setText(decryptedString);
        } catch (Exception e) {
            Log.e("TAG", "onClick: ", e);
        }

    }
}

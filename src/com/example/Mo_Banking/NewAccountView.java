package com.example.Mo_Banking;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.database.Mo_Banking.DatabaseHandler;
import com.definitions.Mo_Banking.Account;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 4/21/13
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */

public class NewAccountView extends Activity {
    private EditText accountNameET;
    private EditText accountNumberET;
    private EditText accountBankNameET;

    private Button saveButton;
    private Button backButton;

    private String accountName;
    private String accountNumber;
    private String bankName;

    private DatabaseHandler databaseHandler;

    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccountview);

        accountNameET     = (EditText) findViewById(R.id.et_newAccount_AccName);
        accountNumberET   = (EditText) findViewById(R.id.et_newAccount_AccNumber);
        accountBankNameET = (EditText) findViewById(R.id.et_newAccount_BankName);

        saveButton = (Button) findViewById(R.id.bt_newAccount_Save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = new Account();
                account.setAcc_name(accountNameET.getText().toString());
                account.setAcc_info(accountBankNameET.getText().toString());
                account.setAcc_number(accountNumberET.getText().toString());

                if(validateInputFields())
                {
                    try {
                        account = new Account();
                        account.setAcc_number(encodeAccountNumber(accountNumber));
                        account.setAcc_name(accountName);
                        account.setAcc_info(bankName);
                        saveAccountInfo(account);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error Validating: Please Check The Fields",Toast.LENGTH_LONG).show();
                }
                // Sending side
//                byte[] data = text.getBytes("UTF-8");
//                String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            }
        });
        backButton = (Button) findViewById(R.id.bt_newAccount_Back);

    }

    private void saveAccountInfo(Account account) {
        databaseHandler = new DatabaseHandler(this);
        databaseHandler.addAccount(account);
    }

    /* TODO: move following functions to the Account class? */
    private boolean validateInputFields() {

        boolean validated = false;

        if ( (!accountName.isEmpty()) && (accountName.length() >= 6)) {
            if (!bankName.isEmpty() && (bankName.length() >=6)) {
                if(!accountNumber.isEmpty() && validateBankNumber(accountNumber)) {
                    validated =  true;
                }
            }
        }
        return validated;
    }

    private boolean validateBankNumber(String IBAN) {

        /**
         * IBAN:		        DE17 1234 5678 5698 7654 32
         * Rearrange:		    123456785698765432 DE17
         * Convert to integer:  3214282912345698765432 13 14 17
         * Compute remainder:	3214282912345698765432 13 14 17	mod 97 = 1
         */

        StringBuilder sb = new StringBuilder();
        StringBuilder computed = new StringBuilder();
        String prefix = IBAN.substring(0,4);
        sb.append(IBAN.substring(4)); //exclude 4 first characters from IBAN
        /**
         * Convert the letters of the prefix in numbers:
         * A = 10
         * B = 11
         * ...
         * Z = 35
         *
         * And append them at the end.
         */
        sb.append(prefix);
        for (char c : sb.toString().toCharArray()) {
            if(Character.isLetter(c))
                computed.append(Character.getNumericValue(Character.toLowerCase(c)));
            else
                computed.append(c);
        }

        Double computedDouble = Double.parseDouble(computed.toString());
        if(computedDouble % 97 == 1) {
            return true;
        }
        else
            return false;
    }

    private String encodeAccountNumber(String account) throws UnsupportedEncodingException {
          return Base64.encodeToString(account.getBytes("UTF-8"), Base64.DEFAULT );
    }


}

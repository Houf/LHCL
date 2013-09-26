package ca.houf.lhcl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import ca.houf.lhcl.R;
import ca.houf.lhcl.database.DatabaseHandler;

public class DatabaseActivity extends Activity
{
    TextView idView;
    EditText productBox;
    EditText quantityBox;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        quantityBox = (EditText) findViewById(R.id.productQuantity);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_database, menu);
        return true;
    }

    public void newProduct(final View view)
    {
        final DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);

        final int quantity = Integer.parseInt(quantityBox.getText().toString());

        final Product product = new Product(productBox.getText().toString(), quantity);

        dbHandler.addProduct(product);
        productBox.setText("");
        quantityBox.setText("");
    }

    public void lookupProduct(final View view)
    {
        final DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);

        final Product product = dbHandler.findProduct(productBox.getText().toString());

        if(product != null)
        {
            idView.setText(String.valueOf(product.getID()));
            quantityBox.setText(String.valueOf(product.getQuantity()));
        }
        else
        {
            idView.setText("No Match Found");
        }
    }

    public void removeProduct(final View view)
    {
        final DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);

        final boolean result = dbHandler.deleteProduct(productBox.getText().toString());

        if(result)
        {
            idView.setText("Record Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }
}

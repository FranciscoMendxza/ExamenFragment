package mx.edu.tesoem.isc.examenfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    Button btn1, btn2, salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btn1 = findViewById(R.id.btnp1);
        btn2 = findViewById(R.id.btnp2);
        salir = findViewById(R.id.btnsalir);

        Fragment funo = new PrimerFragment();
        Fragment fdos = new SegundoFragment();

        btn1.setEnabled(false);
        btn2.setEnabled(false);

        btn1 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f1 = getSupportFragmentManager().beginTransaction();
                f1.replace(R.id.fvcontendenor, fdos).commit();

                btn1.setEnabled(false);
                btn2.setEnabled(true);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f1 = getSupportFragmentManager().beginTransaction();
                f1.replace(R.id.fvcontendenor, funo).commit();
                btn2.setEnabled(false);
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void habilitarBotonCalificar(){
        int aciertos = getIntent().getIntExtra("aciertos", 0);
        int errores = getIntent().getIntExtra("errores", 0);
        int puntos = getIntent().getIntExtra("puntos", 0);
        btn2.setEnabled(true);

        PrimerFragment primerFragment = (PrimerFragment) getSupportFragmentManager().findFragmentById(R.id.fvcontendenor);
        if(primerFragment != null){
            primerFragment.mostrarResultados(aciertos, errores, puntos);
        }
    }
}
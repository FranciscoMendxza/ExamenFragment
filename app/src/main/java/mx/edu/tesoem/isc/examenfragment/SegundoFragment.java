package mx.edu.tesoem.isc.examenfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import DatosExamen.EstructuraDatos;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SegundoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SegundoFragment extends Fragment {

    TextView preguntas;
    Button regresa, siguiente, califica;
    RadioButton RB1, RB2, RB3;
    RadioGroup grupo;
    int contador = 0, aciertos = 0, puntos = 0, errores = 10;
    ArrayList<EstructuraDatos> listadatos = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SegundoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SegundoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SegundoFragment newInstance(String param1, String param2) {
        SegundoFragment fragment = new SegundoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_segundo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        preguntas = getView().findViewById(R.id.txtpreguntas);
        regresa = getView().findViewById(R.id.btnregresar);
        siguiente = getView().findViewById(R.id.btnnext);
        RB1 = getView().findViewById(R.id.rb1);
        RB2 = getView().findViewById(R.id.rb2);
        RB3 = getView().findViewById(R.id.rb3);
        grupo = getView().findViewById(R.id.grupo);

        preguntas();
        siguiente.setEnabled(false);
        regresa.setEnabled(false);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contador < listadatos.size()){
                    String seleccion = seleccionr();
                if(seleccion != null){
                    listadatos.get(contador).setSeleccion(seleccion);
                }
                contador++;
                if (contador < listadatos.size()){
                    EstructuraDatos siguientep = listadatos.get(contador);
                    preguntas.setText(siguientep.getPregunta());
                    RB1.setText(siguientep.getR1());
                    RB2.setText(siguientep.getR2());
                    RB3. setText(siguientep.getR3());

                    grupo.clearCheck();
                }else if (contador == listadatos.size()){
                    siguiente.setEnabled(false);
                }

                }
            }
        });
        regresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador--;

                if(contador >= 0 && contador < listadatos.size()){
                    EstructuraDatos prev = listadatos.get(contador);
                    preguntas.setText(prev.getPregunta());
                    RB1.setText(prev.getR1());
                    RB2.setText(prev.getR2());
                    RB3.setText(prev.getR3());

                    grupo.clearCheck();
                }
            }
        });

        grupo.setOnCheckedChangeListener((radioGroup, i) -> {
            if (RB1.isChecked() || RB2.isChecked() || RB3.isChecked()){ //Si se selecciona alguno, se activa el boton siguiente
                if (contador==9){
                    siguiente.setEnabled(false);
                    ((Principal) requireActivity()).habilitarBotonCalificar();
                }else{
                    siguiente.setEnabled(true);
                }
                regresa.setEnabled(contador != 0);
            }else{
                siguiente.setEnabled(false);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void renvio(){
        if(contador >= 0 && contador < listadatos.size()){
            EstructuraDatos actual = listadatos.get(contador);
            String seleccion = seleccionr();

            if (seleccion != null){
                actual.setSeleccion(seleccion);
            }
        }
        puntos = 0; //se reinicia cada que se califica
        aciertos = 0;
        errores = 10;

        for (EstructuraDatos pregunta : listadatos){
            String respSeleccionada = pregunta.getSeleccion();

            if (respSeleccionada != null){
                if (respSeleccionada.equals(pregunta.getRc())){
                    puntos++;
                    aciertos++;
                    errores--;
                }
            }
        }

        Intent lanza = new Intent(getActivity(), Principal.class);
        lanza.putExtra("aciertos", aciertos);
        lanza.putExtra("errores", errores);
        lanza.putExtra("puntos", puntos);

        startActivity(lanza);
    }
    public void preguntas() {
        EstructuraDatos ed;

        ed = new EstructuraDatos();//Pregunta 1
        ed.setPregunta("Pregunta 1.\n\n ¿Cuántos planetas tiene el sistema solar?");
        ed.setR1("a) 6 planetas");
        ed.setR2("b) 8 planetas");
        ed.setR3("c) 9 planetas");
        ed.setRc("c");
        listadatos.add(ed);

        ed = new EstructuraDatos();//Pregunta 2
        ed.setPregunta("Pregunta 2.\n\n ¿Cuántos colores tiene el arcoiris?");
        ed.setR1("a) 4 colores");
        ed.setR2("b) 6 colores");
        ed.setR3("c) 7 colores");
        ed.setRc("c");
        listadatos.add(ed);

        ed = new EstructuraDatos();//Pregunta 3
        ed.setPregunta("Pregunta 3.\n\n ¿Pesa más 1kg de algodón que 1 kg de metal?");
        ed.setR1("a) Pesa más el metal");
        ed.setR2("b) Pesa más el algodón");
        ed.setR3("c) Pesan lo mismo");
        ed.setRc("c");
        listadatos.add(ed);

        ed = new EstructuraDatos();//Pregunta 4
        ed.setPregunta("Pregunta 4.\n\n ¿Cuál es la estrella más cercana a la tierra?");
        ed.setR1("a) Betelgueuse");
        ed.setR2("b) Sol");
        ed.setR3("c) Las de los reyes magos");
        ed.setRc("b");
        listadatos.add(ed);

        ed = new EstructuraDatos();//Pregunta 5
        ed.setPregunta("Pregunta 5.\n\n ¿A que velocidad va la luz?");
        ed.setR1("a) 344km/h");
        ed.setR2("b) 250km/h");
        ed.setR3("c) 410km/h");
        ed.setRc("a");
        listadatos.add(ed);

        ed = new EstructuraDatos();//Pregunta 6
        ed.setPregunta("Pregunta 6.\n\n ¿Cuál es el elemento químico del Oro?");
        ed.setR1("a) Au");
        ed.setR2("b) Ae");
        ed.setR3("c) Ug");
        ed.setRc("a");
        listadatos.add(ed);

        ed = new EstructuraDatos();//Pregunta 7
        ed.setPregunta("Pregunta 7.\n\n ¿Cuál es la vida promedio de un humano?");
        ed.setR1("a) 50 años");
        ed.setR2("b) 75 años");
        ed.setR3("c) 93 años");
        ed.setRc("b");
        listadatos.add(ed);

        ed = new EstructuraDatos();//Pregunta 8
        ed.setPregunta("Pregunta 8.\n\n ¿Que comen los animales herbívoros?");
        ed.setR1("a) Carne y plantas");
        ed.setR2("b)Plantas e instector");
        ed.setR3("c) Plantas");
        ed.setRc("c");
        listadatos.add(ed);

        ed = new EstructuraDatos();//Pregunta 9
        ed.setPregunta("Pregunta 9.\n\n ¿Cómo se les llama a los gases que rodean la tierra?");
        ed.setR1("a) Troposfera");
        ed.setR2("b) Placas Tectónicas");
        ed.setR3("c) Atmósfera");
        ed.setRc("c");
        listadatos.add(ed);

        ed = new EstructuraDatos();//Pregunta 10
        ed.setPregunta("Pregunta 10.\n\n ¿Cuantas champions ha ganado el Cruz Azul?");
        ed.setR1("a) 8");
        ed.setR2("b) 9");
        ed.setR3("c) ninguna");
        ed.setRc("c");
        listadatos.add(ed);

        EstructuraDatos edm = listadatos.get(0);
        preguntas.setText(edm.getPregunta());
        RB1.setText(edm.getR1());
        RB2.setText(edm.getR2());
        RB3.setText(edm.getR3());
        seleccionr();
    }

    private String seleccionr(){
        if (RB1.isChecked()){
            return "a";
        } else if (RB2.isChecked()) {
            return "b";
        } else if (RB3.isChecked()) {
            return "c";
        }else{
            return null;
        }
    }
}
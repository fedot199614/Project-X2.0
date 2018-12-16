package com.project.usm.app.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import  com.project.usm.app.Model.News;
import  com.project.usm.app.R;
import  com.project.usm.app.Tools.RVAdapter;
import  com.project.usm.app.Tools.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class RV_Main extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RV_Main() {

    }



    public static RV_Main newInstance(String param1, String param2) {
        RV_Main fragment = new RV_Main();
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

        return inflater.inflate(R.layout.fragment_rv__main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        RecyclerView rv = (RecyclerView)getView().findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        List<News> test = new ArrayList<>();

        test.add(new News("În atenția studenților anului I, Specialitățile Tehnologia informației",
                "Sesiunea de instruire (reper) se va desfășura în perioada 15.10.18 – 10.11.18. IV. http://phys.usm.md/ Modificările posibile în Orar (sala), le urmăriți la…",
                "În data de 18 octombrie o delegație a Academiei Forțelor Terestre „Nicolae BĂLCESCU” din Sibiu s-a aflat într-o vizită de lucru la Universitatea de Stat din Moldova. Delegația Academiei a fost condusă de Prorectorul pentru programe și relații internaționale, profesor universitar, doctor Vasile CĂRUȚAȘU.În componența delegației au făcut parte:\n" +
                        "\n" +
                        "Decanul Facultății de Științe Economice și Administrative, prof. univ., doctor Leontin STANCIU;\n" +
                        "Decanul Facultății de Management Militar, conferențiar universitar, doctor inginer Ioan VIRCA;\n" +
                        "profesor universitar, doctor inginer Dănuț MOȘTEANU;\n" +
                        "profesor universitar, doctor Sorin Gheorghe PÎNZARIU.\n" +
                        "Oaspeții au avut o discuțiie de lucru cu Domnul Rector al USM, profesor universitar, doctor habilitat Gheorghe CIOCANU și Prorectorul pentru activitate științifică, profesor universitar, doctor habilitat Florentin PALADI. Conducerile celor două institiuții de învățământ superior s-au informat reciproc referitor la unele performanțe în învățământ, cercetare și oportunități de colaborare. A fost încheiat un Acord de Colaborare între Universitatea de Stat din Moldova și Academia Forțelor Terestre „Nicolae BĂLCESCU”.\n" +
                        "\n" +
                        "Apoi delegația s-a familiarizat cu unele facultăți ale Univesității de Stat din Moldova.\n" +
                        "\n" +
                        "La Facultatea de Fizică și Inginerie Prorectorul pentru activitatea științifică, profesor universitar, doctor habilitat Florentin PALADI; Decanul Facultății Valentina NICORICI, conferențiar universitar, doctor au prezentat Laboratorul „Dronele în instruire”.\n" +
                        "\n" +
                        "Maria BELDIGA, conferențiar universitar, doctor și Gheorghe CĂPĂȚÂNĂ, prof. univ., dr. ing. au prezentat „Sistemul Suport Inteligent de e-Learning la disciplina „Sisteme Suport pentru Decizii””. Sistemul este folosit îcepând cu anul 2013 în procesul de instruire la Facultatea de Fizică și Inginerie.\n" +
                        "\n" +
                        "La Facultatea de Chimie şi Tehnologie Chimică oaspeții au discutat oportunități de colaborare cu Decanul Facultății conferențiar universitar, doctor Doamna Viorica GLADCHI.\n" +
                        "\n" +
                        "Șeful Departamentului Informatică conferențiar universitar, doctor Valeriu UNGUREANU, a familiarizat oaspeții cu Laboratorul Wolfram Mathematicade la Facultatea de Matematică și Informatică.\n" +
                        "\n" +
                        "Decanul Facultății de Pedagogie și Psihologie profesorul universitar, doctor habilitat Vladimir GUȚU a familiarizat oaspeții cu oportunitățile de pregătire a cadrelor de înaltă calificare la Facultate la specialitățile „Pedagogie” și „Psihologie”.\n" +
                        "\n" +
                        "Vizita de lucru a fost finalizată la Facultatea deȘtiințe Economice. La întâlnirea cu delegația Academiei Forțelor Terestre „Nicolae BĂLCESCU” au participat din partea Facultății de Științe Economice:\n" +
                        "\n" +
                        "Ulian Galina – decan, doctor habilitat în economie, profesor universitar;\n" +
                        "\n" +
                        "Mironov Svetlana – prodecan, doctor în economie, conferențiar universitar;\n" +
                        "\n" +
                        "Buzdugan Adriana – prodecan, doctor în economie, conferențiar universitar;\n" +
                        "\n" +
                        "Hamuraru Maria – șef departament Economie, Marketing și Turism, doctor în economie, conferențiar universitar;\n" +
                        "\n" +
                        "Jalencu Marian – șef departament Administrarea afacerilor, doctor în economie, conferențiar universitar;\n" +
                        "\n" +
                        "Dolghi Cristina – șef departament Contabilitatea și Informatica Economică, doctor în economie, conferențiar universitar."));
        test.add(new News("Consiliul Facultății de Fizică și Inginerie (09.10.2018)","La data de 9 octombrie , ora 13.30 in sala 332/4 va avea loc Consiliul facultatii (ordinea de zi se ataseaza.)",
                "gretsd wr sf sdf sdf sd"));

        test.add(new News("Consiliul Facultății de Fizică și Inginerie (09.10.2018)","La data de 9 octombrie , ora 13.30 in sala 332/4 va avea loc Consiliul facultatii (ordinea de zi se ataseaza.)",
                "gretsd wr sf sdf sdf sd"));

        test.add(new News("Consiliul Facultății de Fizică și Inginerie (09.10.2018)","La data de 9 octombrie , ora 13.30 in sala 332/4 va avea loc Consiliul facultatii (ordinea de zi se ataseaza.)",
                "gretsd wr sf sdf sdf sd"));
        test.add(new News("Consiliul Facultății de Fizică și Inginerie (09.10.2018)","La data de 9 octombrie , ora 13.30 in sala 332/4 va avea loc Consiliul facultatii (ordinea de zi se ataseaza.)",
                "gretsd wr sf sdf sdf sd"));
        RVAdapter adapter = new RVAdapter(test);

        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_left));
                setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_right));
                SharedNews sn = new SharedNews();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                sn.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_right));
                Bundle bundle = new Bundle();
                bundle.putString("title", test.get(position).getTitle());
                bundle.putString("news", test.get(position).getFull_news());
                sn.setArguments(bundle);
                transaction.addToBackStack("sharedNews");
                transaction.addSharedElement(view.findViewById(R.id.cv), view.findViewById(R.id.cv).getTransitionName());
                transaction.addSharedElement(view.findViewById(R.id.title_model), view.findViewById(R.id.title_model).getTransitionName());
                transaction.addSharedElement(view.findViewById(R.id.news_model), view.findViewById(R.id.news_model).getTransitionName());
                transaction.replace(R.id.mainFrame,sn).commit();
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}

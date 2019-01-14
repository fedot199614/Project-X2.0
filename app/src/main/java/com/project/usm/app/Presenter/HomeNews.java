package com.project.usm.app.Presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;

import com.project.usm.app.Model.News;
import com.project.usm.app.R;
import com.project.usm.app.Tools.NavItems;
import com.project.usm.app.View.Home_View;

import java.util.ArrayList;
import java.util.List;

public class HomeNews implements IHomeNews {
    Home_View home_view;
    public HomeNews(Home_View home) {

        this.home_view = home;
    }


    @Override
    public void Init(Activity activity) {
        home_view.initTabBar();
        NavItems.getNavMenu(activity).getItem(0).setChecked(true);
        List<News> newsList = new ArrayList<>();
        newsList.add(new News("În atenția studenților anului I, Specialitățile Tehnologia informației",
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
        newsList.add(new News("Consiliul Facultății de Fizică și Inginerie (09.10.2018)","La data de 9 octombrie , ora 13.30 in sala 332/4 va avea loc Consiliul facultatii (ordinea de zi se ataseaza.)",
                "gretsd wr sf sdf sdf sd"));

        newsList.add(new News("Consiliul Facultății de Fizică și Inginerie (09.10.2018)","La data de 9 octombrie , ora 13.30 in sala 332/4 va avea loc Consiliul facultatii (ordinea de zi se ataseaza.)",
                "gretsd wr sf sdf sdf sd"));

        newsList.add(new News("Consiliul Facultății de Fizică și Inginerie (09.10.2018)","La data de 9 octombrie , ora 13.30 in sala 332/4 va avea loc Consiliul facultatii (ordinea de zi se ataseaza.)",
                "gretsd wr sf sdf sdf sd"));
        newsList.add(new News("Consiliul Facultății de Fizică și Inginerie (09.10.2018)","La data de 9 octombrie , ora 13.30 in sala 332/4 va avea loc Consiliul facultatii (ordinea de zi se ataseaza.)",
                "gretsd wr sf sdf sdf sd"));
        RecyclerView rv =  home_view.InitRV();
        rv.setAdapter(home_view.createRVAdapter(newsList));
        home_view.itemClickListener(rv,newsList);
        home_view.onRefresh();


    }

    @Override
    public void setAnimFade(Fragment fragment, Activity activity) {

        Transition changeTransform = TransitionInflater.from(activity).
                inflateTransition(R.transition.change_img_transform);
        fragment.setSharedElementReturnTransition(changeTransform);
        fragment.setSharedElementEnterTransition(changeTransform);

        fragment.setExitTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.explode));
        fragment.setEnterTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.explode));
    }


    @Override
    public void beginTransaction(FragmentManager frManager, Fragment nextFragment, String backStackTag, int position, View view, List<News> model) {
        FragmentTransaction transaction = frManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(view.getResources().getString(R.string.title), model.get(position).getTitle());
        bundle.putString(view.getResources().getString(R.string.news), model.get(position).getFull_news());
        nextFragment.setArguments(bundle);
        transaction.addToBackStack(view.getResources().getString(R.string.sharedNews));
        //transaction.addSharedElement(view.findViewById(R.id.cv), view.findViewById(R.id.cv).getTransitionName());
        //transaction.addSharedElement(view.findViewById(R.id.title_model), view.findViewById(R.id.title_model).getTransitionName());
        //transaction.addSharedElement(view.findViewById(R.id.news_model), view.findViewById(R.id.news_model).getTransitionName());
        transaction.addSharedElement(view.findViewById(R.id.image),"img");
        transaction.replace(R.id.mainFrame,nextFragment).commit();



    }

    @Override
    public void refreshAnim() {
        home_view.runLayoutAnimation();
    }
}

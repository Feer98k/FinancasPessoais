package com.example.financaspessoais.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.financaspessoais.R;
import com.example.financaspessoais.database.DataBaseTransacoes;
import com.example.financaspessoais.database.dao.TransacaoDAO;
import com.example.financaspessoais.model.TipoTransacao;
import com.example.financaspessoais.model.Transacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumoGraficoUsuario extends AppCompatActivity {


    List<String> listaCategoriaDespesas = new ArrayList<>(Arrays.asList(
            "Indefinido", "Casa", "Comida", "Comunicacão", "Contas", "Lazer", "Roupas", "Saúde",
            "Supmercado", "Transporte", "Outros"
    ));
    List<String> listaCategoriaReceita = new ArrayList<>(Arrays.asList(
            "Indefinido", "Economias", "Empréstimo", "Pagamento", "Presente", "Salário", "Vendas", "Outros"
    ));

    double[] valoresDespesa = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    double[] valoresReceita = {0, 0, 0, 0, 0, 0, 0, 0};

    private TransacaoDAO transacaoDAO;
    private List<Transacao> listatransacoes = new ArrayList<>();
    private final List<Transacao> listaTransacoesDespesa = new ArrayList<>();
    private final List<Transacao> listaTransacoesReceita = new ArrayList<>();
    private ImageButton btnResumo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos_usuario);
        carregaComponentes();
        configuraChart1();
        configuraChart2();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.azul_default));
        }
    }

    private void carregaComponentes() {

         btnResumo = findViewById(R.id.main_btn_relatorio_resumo);
        DataBaseTransacoes data = DataBaseTransacoes.Companion.getInstance(this);
        transacaoDAO = data.TransacaoDAO();
        listatransacoes = transacaoDAO.getAllTransacao();
           btnResumo.setOnClickListener(v -> finish());
        configuraTipo();
        setValoresDespesa();
        setValoresReceita();

    }

    private void configuraTipo() {
        for (Transacao a : listatransacoes) {
            if (a.getTipo() == TipoTransacao.DESPESA) {
                listaTransacoesDespesa.add(a);
            } else {
                listaTransacoesReceita.add(a);
            }
        }
    }

    public void setValoresReceita() {
        for (Transacao a : listaTransacoesReceita) {
            if (a.getCategoria().equals("Receita indefinida")) {
                double p00 = valoresReceita[0];
                p00 += a.getValor();
                valoresReceita[0] = p00;
            }
            if (a.getCategoria().equals("Economias")) {
                double p01 = valoresReceita[1];
                p01 += a.getValor();
                valoresReceita[1] = p01;
            }
            if (a.getCategoria().equals("Empréstimo")) {
                double p02 = valoresReceita[2];
                p02 += a.getValor();
                valoresReceita[2] = p02;
            }
            if (a.getCategoria().equals("Pagamento")) {
                double p03 = valoresReceita[4];
                p03 += a.getValor();
                valoresReceita[3] = p03;
            }
            if (a.getCategoria().equals("Presente")) {
                double p04 = valoresReceita[4];
                p04 += a.getValor();
                valoresReceita[4] = p04;
            }
            if (a.getCategoria().equals("Salário")) {
                double p05 = valoresReceita[5];
                p05 += a.getValor();
                valoresReceita[5] = p05;
            }
            if (a.getCategoria().equals("Vendas")) {
                double p06 = valoresReceita[6];
                p06 += a.getValor();
                valoresReceita[6] = p06;
            }
            if (a.getCategoria().equals("Outros")) {
                double p07 = valoresReceita[7];
                p07 += a.getValor();
                valoresReceita[7] = p07;
            }
        }
    }

    private void setValoresDespesa() {
        for (Transacao a : listaTransacoesDespesa) {
            if (a.getCategoria().equals("Despesa indefinida")) {
                double p00 = valoresDespesa[0];
                p00 += a.getValor();
                valoresDespesa[0] = p00;
            }
            if (a.getCategoria().equals("Casa")) {
                double p01 = valoresDespesa[1];
                p01 += a.getValor();
                valoresDespesa[1] = p01;
            }
            if (a.getCategoria().equals("Comida")) {
                double p02 = valoresDespesa[2];
                p02 += a.getValor();
                valoresDespesa[2] = p02;
            }
            if (a.getCategoria().equals("Comunicações")) {
                double p03 = valoresDespesa[3];
                p03 += a.getValor();
                valoresDespesa[3] = p03;
            }

            if (a.getCategoria().equals("Contas")) {
                double p04 = valoresDespesa[4];
                p04 += a.getValor();
                valoresDespesa[4] = p04;
            }
            if (a.getCategoria().equals("Lazer")) {
                double p05 = valoresDespesa[5];
                p05 += a.getValor();
                valoresDespesa[5] = p05;
            }
            if (a.getCategoria().equals("Roupas")) {
                double p06 = valoresDespesa[6];
                p06 += a.getValor();
                valoresDespesa[6] = p06;
            }
            if (a.getCategoria().equals("Saúde")) {
                double p07 = valoresDespesa[7];
                p07 += a.getValor();
                valoresDespesa[7] = p07;
            }
            if (a.getCategoria().equals("Supermercado")) {
                double p08 = valoresDespesa[8];
                p08 += a.getValor();
                valoresDespesa[8] = p08;
            }
            if (a.getCategoria().equals("Transporte")) {
                double p09 = valoresDespesa[9];
                p09 += a.getValor();
                valoresDespesa[9] = p09;
            }
            if (a.getCategoria().equals("Outros")) {
                double p10 = valoresDespesa[10];
                p10 += a.getValor();
                valoresDespesa[10] = p10;
            }
        }
    }

    public void configuraChart1() {
        AnyChartView anychart = findViewById(R.id.grafico_despesa);
        APIlib.getInstance().setActiveAnyChartView(anychart);
        Pie pieDespesa = AnyChart.pie();
        List<DataEntry> dataEntriesDespesa = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            dataEntriesDespesa.add(new ValueDataEntry(listaCategoriaDespesas.get(i), valoresDespesa[i]));
        }
        pieDespesa.data(dataEntriesDespesa);
        pieDespesa.title("Resumo de Despesas");
        anychart.setChart(pieDespesa);

    }

    public void configuraChart2() {
        AnyChartView anychart2 = findViewById(R.id.grafico_receita);
        APIlib.getInstance().setActiveAnyChartView(anychart2);
        Pie pieReceita = AnyChart.pie();
        List<DataEntry> dataEntriesReceita = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            dataEntriesReceita.add(new ValueDataEntry(listaCategoriaReceita.get(i), valoresReceita[i]));
        }
        pieReceita.data(dataEntriesReceita);
        pieReceita.title("Resumo de Receita");
        anychart2.setChart(pieReceita);


    }
}

import javax.swing.JLabel;
// import static programa.VentanaCronometromin;
// import static programa.VentanaCronometrohor;
// import static programa.VentanaCronometroseg;
// import static programa.VentanaCronometroinicia;
// import static programa.VentanaCronometrorun;

public class Cronometro extends Thread {
    JLabel eti;

    public Cronometro(JLabel cronometro) {
        this.eti = cronometro;
    }

    public void run() {
        try {
            int x = 0;
            while (programa.VentanaCronometrorun) {
                Thread.sleep(100);
                ejecutarHiloCronometro(x);
                x++;
            }
        } catch (Exception e) {
        }
    }

    private void ejecutarHiloCronometro(int x) {
        programa.VentanaCronometroseg++;
        if (programa.VentanaCronometroseg > 59) {
            programa.VentanaCronometroseg = 0;
            programa.VentanaCronometromin++;
            if (programa.VentanaCronometromin > 59) {
                programa.VentanaCronometromin = 0;
                programa.VentanaCronometrohor++;
            }
        }
        String textSeg = "", textMin = "", textHora = "";
        if (programa.VentanaCronometroseg < 10)
            textSeg = "0" + programa.VentanaCronometroseg;
        else
            textSeg = "" + programa.VentanaCronometroseg;
        if (programa.VentanaCronometromin < 10)
            textMin = "0" + programa.VentanaCronometromin;
        else
            textMin = "" + programa.VentanaCronometromin;
        if (programa.VentanaCronometrohor < 10)
            textHora = "0" + programa.VentanaCronometrohor;
        else
            textHora = "" + programa.VentanaCronometrohor;
        String reloj = textHora + ":" + textMin + ":" + textSeg;
        eti.setText(reloj);
    }

}

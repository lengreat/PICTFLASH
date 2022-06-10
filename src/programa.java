
import java.awt.Color;
import java.awt.Font;

// import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class programa extends JFrame {

    int modulo = 1;
    String ruta = " ";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
    int INCREMENTO = 0;// tamanio de filas quitadas
    String pathImagenes = " ";
    int MAX = 0;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                // excel
    ImageIcon imagen[];
    int arregloAleatorios[];
    static String valorCeldaIngles[];
    static String valorCeldaSpanish[];
    static String valorCeldaPronunciacion[];

    static int VentanaCronometroseg;
    static int VentanaCronometromin;
    static int VentanaCronometrohor;
    static boolean VentanaCronometroinicia = true;
    static boolean VentanaCronometrorun = true;
    int ENTERS = MAX + 1;
    int contador = 0;
    boolean reinicio = true;
    boolean paradaTrue = false;
    static int al = 0;
    static int band = 0;
    int tamanoExcel = 0;
    int min, seg, mseg = 0;
    static Cronometro cronometro;
    int contadorEnters = 0;
    int permisivoDesorden = 0;
    static String titular = "";
    private int aleatorio;
    private boolean bandStart = true;
    private boolean banTecleo = true;
    private boolean bandReset=false;
    private boolean inicioEnterPalabra = false;

    private JButton reset;
    private JLabel PantallaImagen;
    private JLabel jLabel2;
    private JLabel showerText;
    private JLabel showCronometer;
    private JLabel contadorPush;
    private JTextField push;
    private boolean bandStop;

    private JButton stop;
    private JComboBox opciones;
    private JButton start;
    private JLabel titulo;
    private JRadioButton tecleo;
    private JRadioButton mostarEspanish;
    private JComboBox mix;
    private JButton mixText;
    private JScrollPane CSpanish;
    private JTextPane spanish;
    private JLabel pronunciacion;
    private JTextField tecleoPalabra;
    private JLabel mensajeEnter;
    private JLabel mensajeTecleoYEnter;

    public programa() {

        diseno();
        push.setVisible(false);
        reset.setEnabled(false);
        stop.setEnabled(false);

        // initComponents();
        this.setTitle("PICTFLASH");
        opcionesModuloIngles();
        llenadoNombresImagenes();// Arreglo de enlace a imagenes
        creacionArregloAleatorios();// Crea e imprime un arreglo del 1 a MAX
        PantallaImagen.setIcon(imagen[0]);// Poniendo la imagen Inicial

        LecturaValoresIngles();// Lectura de valores en ingles del excel y llenado
        ImprimirArreglo();// datos en ingles impresos en pantalla

        eventosPush();// cada q doy enter
        eventosReset();// Al dar reset
        eventosStart();
        eventosMostrarEspanish();
        eventosPushTecleo();
        eventosStop();
        mix.setEnabled(false);
        mixText.setEnabled(false);

    }

    private void diseno() {
        setSize(1000, 600);
        Font font1 = new Font("Serif", Font.BOLD, 28);
        reset = new JButton("R");
        reset.setFont(font1);
        reset.setBounds(650, 470, 70, 70);

        stop = new JButton("S");
        stop.setFont(font1);
        stop.setBounds(570, 470, 70, 70);
        add(stop);

        contadorPush = new JLabel(" ", JLabel.CENTER);
        contadorPush.setFont(font1);
        contadorPush.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contadorPush.setBounds(20, 470, 70, 70);

        push = new JTextField();
        push.setFont(font1);
        push.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        push.setBounds(100, 470, 50, 70);

        showerText = new JLabel("CONTENIDO", JLabel.CENTER);
        showerText.setFont(font1);
        showerText.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        showerText.setBounds(160, 470, 400, 70);

        tecleoPalabra = new JTextField("", 1);
        tecleoPalabra.setFont(font1);
        tecleoPalabra.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        tecleoPalabra.setBounds(160, 470, 400, 70);
        tecleoPalabra.setBorder(BorderFactory.createCompoundBorder(tecleoPalabra.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 6, 6)));
        tecleoPalabra.setVisible(false);
        add(tecleoPalabra);

        showCronometer = new JLabel("TIME", JLabel.CENTER);
        showCronometer.setFont(font1);
        showCronometer.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        showCronometer.setBounds(740, 470, 200, 70);

        PantallaImagen = new JLabel("", JLabel.CENTER);
        PantallaImagen.setFont(font1);
        PantallaImagen.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        PantallaImagen.setBounds(20, 60, 700, 400);

        opciones = new JComboBox<Integer>();
        opciones.setFont(font1);
        for (int a = 1; a <= 26; a++) {
            opciones.addItem(a);
        }
        opciones.setBounds(770, 20, 60, 50);
        add(opciones);

        mix = new JComboBox<Integer>();
        mix.setFont(font1);
        mix.addItem(30);
        mix.addItem(80);
        mix.addItem(140);
        mix.setBounds(770, 160, 70, 50);
        add(mix);
        mixText = new JButton("MIX");
        mixText.setBackground(Color.green);
        mixText.setBounds(850, 160, 90, 50);
        mixText.setFont(font1);
        add(mixText);

        start = new JButton("START");
        start.setBackground(Color.GREEN);
        start.setBounds(840, 20, 90, 50);
        add(start);

        mensajeEnter = new JLabel("--- PRESIONE ENTER ----");
        mensajeEnter.setBounds(800, 65, 250, 50);
        add(mensajeEnter);
        mensajeEnter.setVisible(false);

        titulo = new JLabel("--- INICIO ----");
        titulo.setFont(font1);
        titulo.setBounds(350, 10, 250, 50);
        add(titulo);

        tecleo = new JRadioButton("TECLEAR");
        tecleo.setBounds(800, 100, 130, 50);
        add(tecleo);
        tecleo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                banTecleo = false;
                Resetear();
                banTecleo = true;
                if (tecleo.isSelected()) {
                    showerText.setVisible(false);
                    tecleoPalabra.setVisible(true);
                    tecleoPalabra.setText("PRESIONE START");
                    tecleoPalabra.setEditable(false);
                    push.setVisible(false);
                } else {
                    showerText.setVisible(true);
                    tecleoPalabra.setVisible(false);
                    push.setVisible(true);
                }

            }
        });

        mostarEspanish = new JRadioButton("MOSTRAR ESPAÑOL");
        mostarEspanish.setBounds(770, 220, 170, 50);
        add(mostarEspanish);

        spanish = new JTextPane();
        spanish.setText("VALORES EN ESPANOL");
        spanish.setEditable(false);
        spanish.setVisible(false);
        CSpanish = new JScrollPane(spanish);
        CSpanish.setBounds(740, 270, 200, 100);
        CSpanish.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(CSpanish);

        pronunciacion = new JLabel("PRONUNCIACION", JLabel.CENTER);
        pronunciacion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pronunciacion.setBounds(740, 400, 200, 40);
        pronunciacion.setVisible(false);
        add(pronunciacion);

        add(reset);
        add(contadorPush);
        add(push);
        add(showerText);
        add(showCronometer);
        add(PantallaImagen);
        setLayout(null);
        setVisible(true);
    }

    private void eventosStart() {
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (tecleo.isSelected()) {
                    System.out.println(titular);
                    tecleoPalabra.setEnabled(true);
                    tecleoPalabra.setEditable(true);
                    tecleoPalabra.setText("PRESIONE ENTER");
                    Resetear();
                    // tecleoPalabra.setEditable(false);
                    tecleoPalabra.requestFocus();
                    push.setVisible(false);
                } else {
                    push.setVisible(true);
                    push.setEditable(true);
                    push.setText("");
                    push.requestFocus();
                    mensajeEnter.setVisible(true);
                    Resetear();
                    bandStart = false;
                    titulo.setText("--- INICIO ----");
                }
            }
        });
    }

    private void eventosMostrarEspanish() {
        mostarEspanish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!mostarEspanish.isSelected()) {
                    spanish.setVisible(false);
                    pronunciacion.setVisible(false);
                } else {
                    spanish.setVisible(true);
                    pronunciacion.setVisible(true);
                }
            }
        });
    }

    private void eventosStop() {
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (bandStop) {
                    if (!bandReset){

                        cronometro.resume();
                        bandReset=false;
                    }
                    push.setEnabled(true);
                    stop.setBackground(Color.LIGHT_GRAY);
                    if (tecleo.isSelected()) {
                        tecleoPalabra.setEditable(true);
                        tecleoPalabra.requestFocus();
                        // tecleoPalabra.setEditable(false);

                    } else {

                        push.requestFocus();
                    }
                    System.out.println("Verdadero");
                    reset.setEnabled(false);
                } else {
                    cronometro.suspend();
                    push.setEnabled(false);
                    tecleoPalabra.setEditable(false);
                    stop.setBackground(Color.green);
                    reset.setEnabled(true);
                    System.out.println("false");
                }

                bandStop = !bandStop;
            }
        });
    }

    private void eventosReset() {
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // if (!tecleo.isSelected())
                Resetear();
            }
        });
    }

    private void Resetear() {
        if (banTecleo) {// si no acciono el combobox
            start.setEnabled(false);
            mix.setEnabled(false);
            mixText.setEnabled(false);
            opciones.setEnabled(false);
            tecleo.setEnabled(false);
        }
        VentanaCronometromin = 0;
        VentanaCronometroseg = 0;
        VentanaCronometrohor = 0;
        VentanaCronometroinicia = true;
        VentanaCronometrorun = true;
        contadorEnters = 0;
        reset.setEnabled(false);// no se puede volver a accionar
        contadorPush.setText("0");
        showCronometer.setText("00:00:00");
        reinicio = false;// esta variable no sirvio
        paradaTrue = false;
        permisivoDesorden = 0;
        band = 0;
        banTecleo = true;
        bandReset=true;
        //bandStop=!bandStop;
        if (!tecleo.isSelected()) {
            push.setEnabled(true);
            push.setEditable(true);
            push.setText("");
            push.requestFocus();
            //push.setEditable(false);
        } else {
            tecleoPalabra.setEditable(true);
            tecleoPalabra.setEnabled(true);
            tecleoPalabra.requestFocus();
        }
        if (tecleo.isSelected())
        mensajeEnter.setText("-- ESCRIBA  --");
        else
        mensajeEnter.setText("--- PRESIONE ENTER ----");
        if (!banTecleo)// si el restar viene del Tecleo comboboz
        mensajeEnter.setVisible(false);
        else
        mensajeEnter.setVisible(true);
        if ((!bandStart) & (banTecleo)) // ppara q no cierre un hilo q nunca se a abierto
        cronometro.interrupt();//para q no cierre el hilo al dar click al radioboton y tampoco al dar start
        //stop.doClick();
        System.out.println(bandStart);
        System.out.println(banTecleo);
        }   

    private void eventosPushTecleo() {
        tecleoPalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String enterIngresado = tecleoPalabra.getText();
                if (tecleo.isSelected() & enterIngresado.equals("PRESIONE ENTER")) {
                    //stop.setEnabled(true);
                    titulo.setText(titular);
                    if (VentanaCronometroinicia) {
                        cronometro = new Cronometro(showCronometer);
                        cronometro.start();
                        VentanaCronometroinicia = false;
                    }
                    if (permisivoDesorden == 0) {// Al primer enter se desordena
                        tecleoPalabra.setEditable(true);
                        modulo = opciones.getSelectedIndex() + 1;
                        opcionesModuloIngles();// Me da los valores para empezar
                        llenadoNombresImagenes();// Arreglo de enlace a imagenes
                        creacionArregloAleatorios();// Crea e imprime un arreglo del 1 a MAX
                        LecturaValoresIngles();// Lectura de valores en ingles del excel y llenado
                        ImprimirArreglo();// datos en ingles impresos en pantalla
                        titulo.setText(titular);
                        desorden_arregloAleatorios();
                        mostrarArregloAleatorios();
                        permisivoDesorden++;
                    }
                    //MAX = 4;

                    PantallaImagen.setIcon(imagen[arregloAleatorios[contadorEnters] - 1]);
                    spanish.setText("----------");
                    pronunciacion.setText("----------");

                    tecleoPalabra.setText("");
                    tecleoPalabra.setEditable(true);
                    // tecleoPalabra.requestFocus();
                    contadorPush.setForeground(Color.black);
                }
                if (enterIngresado.equals(valorCeldaIngles[arregloAleatorios[contadorEnters] - 1])
                        || (enterIngresado.equals("paso"))) {
                            if (enterIngresado.equals("paso"))
                               titulo.setText(valorCeldaIngles[arregloAleatorios[contadorEnters] - 1]);
                
                    tecleoPalabra.setText("PRESIONE ENTER");
                    // tecleoPalabra.requestFocus();
                    tecleoPalabra.setEditable(false);
                    spanish.setText(valorCeldaSpanish[arregloAleatorios[contadorEnters] - 1]);
                    pronunciacion.setText(valorCeldaPronunciacion[arregloAleatorios[contadorEnters] - 1]);
                    contadorPush.setForeground(Color.red);
                    contadorEnters++;
                    contadorPush.setText(Integer.toString(contadorEnters));// Mostrando cantidad de enter
                    System.out.println(contadorEnters);
                }
                if (contadorEnters == MAX || enterIngresado.equals("fin")) {
                    if (enterIngresado.equals("fin")){
                        tecleoPalabra.setText("PRESIONE ENTER");
                        tecleoPalabra.setEditable(false);
                    }
                    VentanaCronometrorun = false;
                    // push.setText("X");
                    // push.setEditable(false);
                    tecleoPalabra.setEditable(false);
                    tecleoPalabra.setEnabled(false);

                    reset.setEnabled(true);
                    mensajeEnter.setVisible(false);
                    paradaTrue = true;
                    start.setEnabled(true);
                    opciones.setEnabled(true);
                    //mix.setEnabled(true);
                    //mixText.setEnabled(true);
                    tecleo.setEnabled(true);
                }

                System.out.println("presionado");

            }// action Performed
        });
    }

    private void eventosPush() {
        push.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String enterIngresado = push.getText();
                if (!tecleo.isSelected())
                    if (enterIngresado.equals("")) {
                        contadorPush.setForeground(Color.black);
                        if (paradaTrue == false)// Inhabilitar el reset
                            reset.setEnabled(false);
                        band++;// utilizado para dos enter
                        // enterIngresado = push.getText();
                        if (permisivoDesorden == 0) {// Al primer enter se desordena
                            //stop.setEnabled(true);
                            modulo = opciones.getSelectedIndex() + 1;
                            opcionesModuloIngles();
                            llenadoNombresImagenes();// Arreglo de enlace a imagenes
                            creacionArregloAleatorios();// Crea e imprime un arreglo del 1 a MAX
                            LecturaValoresIngles();// Lectura de valores en ingles del excel y llenado
                            ImprimirArreglo();// datos en ingles impresos en pantalla
                            titulo.setText(titular);
                            desorden_arregloAleatorios();
                            mostrarArregloAleatorios();
                            permisivoDesorden++;
                        }
                        // permisivoDesorden=1;//colocarArriba
                        if (enterIngresado.equals("") && band == 1) {// Mostrando imagen
                            contadorEnters++;
                            PantallaImagen.setIcon(imagen[arregloAleatorios[contadorEnters - 1] - 1]);
                            showerText.setText("----------");
                            spanish.setText("----------");
                            pronunciacion.setText("----------");
                        } else if (paradaTrue == false) {// Mostrando texto
                            showerText.setText(valorCeldaIngles[arregloAleatorios[contadorEnters - 1] - 1]);
                            spanish.setText(valorCeldaSpanish[arregloAleatorios[contadorEnters - 1] - 1]);
                            pronunciacion.setText(valorCeldaPronunciacion[arregloAleatorios[contadorEnters - 1] - 1]);
                            contadorPush.setForeground(Color.red);
                            band = 0;
                        }

                        contadorPush.setText(Integer.toString(contadorEnters));// Mostrando cantidad de enter
                        if (contadorEnters == MAX && band == 0) {
                            VentanaCronometrorun = false;
                            push.setText("X");
                            push.setEditable(false);
                            reset.setEnabled(true);
                            mensajeEnter.setVisible(false);
                            paradaTrue = true;
                            start.setEnabled(true);
                            opciones.setEnabled(true);
                            //mix.setEnabled(true);
                            //mixText.setEnabled(true);
                            tecleo.setEnabled(true);
                            stop.setEnabled(false);
                        }

                        if (VentanaCronometroinicia) {
                            cronometro = new Cronometro(showCronometer);
                            cronometro.start();
                            VentanaCronometroinicia = false;
                        }

                    } // end if

            }// action Performed
        });
    }

    private void disenoAnterior() {
        setSize(1000, 600);
        Font font1 = new Font("Serif", Font.BOLD, 28);
        reset = new JButton("R");
        reset.setFont(font1);
        reset.setBounds(650, 470, 70, 70);

        contadorPush = new JLabel(" ", JLabel.CENTER);
        contadorPush.setFont(font1);
        contadorPush.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contadorPush.setBounds(20, 470, 70, 70);

        push = new JTextField();
        push.setFont(font1);
        push.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        push.setBounds(120, 470, 70, 70);

        showerText = new JLabel("CONTENIDO", JLabel.CENTER);
        showerText.setFont(font1);
        showerText.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        showerText.setBounds(220, 470, 400, 70);

        showCronometer = new JLabel("CONTEN", JLabel.CENTER);
        showCronometer.setFont(font1);
        showCronometer.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        showCronometer.setBounds(730, 470, 200, 70);

        PantallaImagen = new JLabel("", JLabel.CENTER);
        PantallaImagen.setFont(font1);
        PantallaImagen.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        PantallaImagen.setBounds(20, 0, 900, 400);

        add(reset);
        add(contadorPush);
        add(push);
        add(showerText);
        add(showCronometer);
        add(PantallaImagen);
        setLayout(null);
        setVisible(true);
    }

    private void mostrarArregloAleatorios() {
        for (int i = 0; i < arregloAleatorios.length; i++) {
            System.out.print(arregloAleatorios[i] + " ");
        }
        System.out.println();
    }

    private void desorden_arregloAleatorios() {
        int temp = 0;
        for (int i = 0; i < arregloAleatorios.length; i++) {
            aleatorio = (int) ((Math.random() * arregloAleatorios.length) + 1);// NUmeros del 1 al arreglo.length
            temp = arregloAleatorios[aleatorio - 1];
            arregloAleatorios[aleatorio - 1] = arregloAleatorios[i];
            arregloAleatorios[i] = temp;
        }
        System.out.println("Arreglo desordenado");
    }

    public void ImprimirArreglo() {
        for (int i = 0; i < tamanoExcel; i++)
            System.out.println(valorCeldaIngles[i]);
        System.out.println(tamanoExcel);
    }

    public void LecturaValoresIngles() {
        try {
            // obtencion del directorio actual
            String currentDir = System.getProperty("user.dir");// obtener el directorio del proyecto automaticamente
            System.out.println("Current dir using System:" + currentDir);
            // https://www.iteramos.com/pregunta/6300/obtencion-del-directorio-de-trabajo-actual-en-java

            // FileInputStream archivo = new FileInputStream(new
            // File("C:/Users/DETPC/Documents/JavaVSCodeProjects/LecturaImagenes/ReadingImages/src/"+
            // ruta));
            FileInputStream archivo = new FileInputStream(new File(currentDir + "/src" + "/excel/" + ruta));
            XSSFWorkbook libroLectura = new XSSFWorkbook(archivo);
            XSSFSheet hojaLectura = libroLectura.getSheetAt(0);
            int numFilas = hojaLectura.getLastRowNum();
            int numCol = 0;
            Row fila;
            Cell celdaIngles, celdaSpanish, celdaPronunciacion;
            tamanoExcel = numFilas + 1;
            valorCeldaIngles = new String[tamanoExcel];
            valorCeldaSpanish = new String[tamanoExcel];
            valorCeldaPronunciacion = new String[tamanoExcel];
            for (int i = 0; i <= numFilas; i++) {
                fila = hojaLectura.getRow(i);// Obtencion de fila por fila
                numCol = fila.getLastCellNum();// obtencion de total de columnas
                celdaIngles = fila.getCell(numCol - 1);// obtencion de la celda
                celdaSpanish = fila.getCell(numCol - 2);
                celdaPronunciacion = fila.getCell(numCol - 3);
                valorCeldaIngles[i] = celdaIngles.getStringCellValue();
                valorCeldaSpanish[i] = celdaSpanish.getStringCellValue();
                valorCeldaPronunciacion[i] = celdaPronunciacion.getStringCellValue();

            }
        } catch (Exception e) {
            System.err.println("Err: " + e);
        }
    }

    private void creacionArregloAleatorios() {

        for (int i = 0; i < MAX; i++) {
            arregloAleatorios[i] = (i + 1);
            System.out.print(arregloAleatorios[i] + " ");
        }

    }

    public void llenadoNombresImagenes() {
        for (int i = 0; i < MAX; i++) {
            imagen[i] = new ImageIcon(
                    getClass().getResource("/" + "pictures/" + pathImagenes + "/fot" + (i + 1 + INCREMENTO) + ".jpg"));
        }
    }

    private void opcionesModuloIngles() {

        switch (modulo) {

            case 1:
                ruta = "SpanishEnglish-CASA1.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenes";
                MAX = 30;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Objetos";
                break;
            case 2:
                ruta = "SpanishEnglish-CASA2.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 30;// tamanio de filas quitadas
                pathImagenes = "imagenes";
                MAX = 31;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Objetos";
                break;
            case 3:
                ruta = "SpanishEnglish-CASA3.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesCasa3";
                MAX = 30;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Objetos";
                break;
            case 4:
                ruta = "SpanishEnglish-CASA4.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesCasa4";
                MAX = 30;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Objetos";
                break;
            case 5:
                ruta = "SpanishEnglish-CASA5.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesCasa5";
                MAX = 30;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Objetos";
                break;
            case 6:
                ruta = "SpanishEnglish-CASA6.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesCasa6";
                MAX = 30;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Objetos";
                break;
            case 7:
                ruta = "SpanishEnglish-CASA7.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesCasa7";
                MAX = 30;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Objetos";
                break;
            case 8:
                ruta = "SpanishEnglish-CLOTH.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesCloth";
                MAX = 32;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Ropa";
                break;
            case 9:
                ruta = "SpanishEnglish-CLOTH2.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesCloth2";
                MAX = 32;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Ropa";
                break;
            case 10:
                ruta = "SpanishEnglish-BODY1.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesBody1";
                MAX = 34;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "cuerpo humano";
                break;
            case 11:
                ruta = "SpanishEnglish-BODY2.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesBody2";
                MAX = 31;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "cuerpo humano";
                break;
            case 12:
                ruta = "SpanishEnglish-VERBS1.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs1";
                MAX = 34;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 13:
                ruta = "SpanishEnglish-VERBS2.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs2";
                MAX = 32;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 14:
                ruta = "SpanishEnglish-VERBS3.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs3";
                MAX = 32;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";

                break;
            case 15:
                ruta = "SpanishEnglish-VERBS4.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs4";
                MAX = 32;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";

                break;
            case 16:
                ruta = "SpanishEnglish-VERBS5.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs5";
                MAX = 35;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 17:
                ruta = "SpanishEnglish-VERBS6.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs6";
                MAX = 36;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 18:
                ruta = "SpanishEnglish-VERBS7.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs7";
                MAX = 30;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 19:
                ruta = "SpanishEnglish-VERBS8.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs8";
                MAX = 35;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 20:
                ruta = "SpanishEnglish-VERBS9.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs9";
                MAX = 35;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 21:
                ruta = "SpanishEnglish-VERBS10.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs10";
                MAX = 34;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 22:
                ruta = "SpanishEnglish-VERBS11.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs11";
                MAX = 34;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 23:
                ruta = "SpanishEnglish-VERBS12.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs12";
                MAX = 35;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 24:
                ruta = "SpanishEnglish-VERBS13.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs13";
                MAX = 34;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 25:
                ruta = "SpanishEnglish-VERBS14.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs14";
                MAX = 33;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 26:
                ruta = "SpanishEnglish-VERBS15.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesVerbs15";
                MAX = 28;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                titular = "Verbos";
                break;
            case 27:
                ruta = "SpanishEnglish-SUSTANTIVES1.xlsx";// CAMBIAR RUTA, INCREMENTO, Y MAX
                                                          // ******************************
                INCREMENTO = 0;// tamanio de filas quitadas
                pathImagenes = "imagenesSustantives1";
                MAX = 31;// SE CAMBIA RESPECTO A LA CANTIDAD DE IMAGENES sin importar el tamanio del
                         // excel
                break;

            default:
                break;
        }

        imagen = new ImageIcon[MAX];
        arregloAleatorios = new int[MAX];

    }
}

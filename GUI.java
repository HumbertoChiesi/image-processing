import histograma.Histograma;
import Filtros.Filtros;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class GUI extends JFrame{
    BufferedImage imagem;

    private JToolBar barraComandos = new JToolBar();
    private JPanel painelImagem = new JPanel();

    private JButton jbHistograma = new JButton("Histograma");
    private JButton jbMenuFiltros = new JButton("Filtros");
    private JButton jbSalvar = new JButton("Salvar");
    private JButton jbReiniciar = new JButton("Reiniciar");
    private JButton jbSair = new JButton("Sair");

    private JPopupMenu menu = new JPopupMenu();
    private JMenuItem jbPCinza = new JMenuItem("P. de cinza");
    private JMenuItem jbBinario = new JMenuItem("Binario");
    private JMenuItem jbFiltroX = new JMenuItem("Gaussiano");
    private JMenuItem jbFiltroY = new JMenuItem("Emboss");
    private JMenuItem jbCustomizado = new JMenuItem("Customizado");


    public GUI(BufferedImage imagem){
        super("Processamento de imagem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(imagem.getWidth(), imagem.getHeight() + 75);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        this.imagem = imagem;

        barraComandos.add(jbHistograma);
        barraComandos.add(jbMenuFiltros);
        barraComandos.add(jbSalvar);
        barraComandos.add(jbReiniciar);
        barraComandos.add(jbSair);

        menu.add(jbPCinza);
        menu.add(jbBinario);
        menu.add(jbFiltroX);
        menu.add(jbFiltroY);
        menu.add(jbCustomizado);

        painelImagem.add(new JLabel(new ImageIcon(imagem)));

        add(barraComandos, BorderLayout.NORTH);
        add(painelImagem  , BorderLayout.CENTER);

        jbHistograma.addActionListener(e -> new Histograma(imagem));

        jbMenuFiltros.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e){
                if ( e.getButton() == 1 ){ // 1-left, 2-middle, 3-right button
                   menu.show(jbMenuFiltros, e.getX(), e.getY());
                }
            }
        });

        jbSalvar.addActionListener(e -> salvarImagem());

        jbReiniciar.addActionListener(e -> {
            setVisible(false);
            dispose();
            App.main(null);
        });

        jbSair.addActionListener(e -> System.exit(0));

        jbPCinza.addActionListener(e -> {
            new GUI(Filtros.pCinza(imagem));
            setVisible(false);
            dispose();
        });
        jbBinario.addActionListener(e -> {
            new GUI(Filtros.binario(imagem));
            setVisible(false);
            dispose();
        });
        jbFiltroX.addActionListener(e -> {
            new GUI(Filtros.gaussiano(imagem));
            setVisible(false);
            dispose();
        });
        jbFiltroY.addActionListener(e -> {
            new GUI(Filtros.laplaciano(imagem));
            setVisible(false);
            dispose();
        });
        jbCustomizado.addActionListener(e -> {
            JPanel painel = new JPanel();
            painel.setLayout(new GridLayout(3, 3));
            JTextField[] fields = new JTextField[9];
            for (int i = 0; i<9;i++){
                fields[i] = new JTextField(2);
                painel.add(fields[i]);
            }
            int result = JOptionPane.showConfirmDialog(null, painel,
                    "Configuracoes do jogo", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION){
                float[] aux = new float[9];
                for (int i = 0; i<9; i++){
                    try{
                        aux[i] = Float.parseFloat(fields[i].getText());
                    } catch (Exception err){
                        JOptionPane.showMessageDialog(null, "ERRO ao carregar matriz!!", "ERRO", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }
                new GUI(Filtros.aplicarFiltro(imagem, aux));
                setVisible(false);
                dispose();
            }
        });
    }

    private void salvarImagem(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));
        chooser.setCurrentDirectory(new File("./imagens"));

        int resultado = chooser.showSaveDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File f = new File(chooser.getSelectedFile()+".png");
                ImageIO.write(imagem, "png", f);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

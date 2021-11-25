import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class InputGui extends JFrame{
    BufferedImage imagem;
    JFileChooser fileChooser;
    boolean sucesso = false;

    JButton urlButton = new JButton("URL");
    JButton buscarArquivoButton = new JButton("Buscar em arquivos");

    public InputGui(){
        super("Metodo para buscar imagem");
        setVisible(true);
        setSize(350, 100);
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        add(urlButton);
        add(buscarArquivoButton);

        urlButton.addActionListener(e -> {
            mostrarURLInput();
            if (sucesso){
                setVisible(false);
                dispose();
            }
        });
        buscarArquivoButton.addActionListener(e -> {
            mostrarBuscarInput();
            if (sucesso){
                setVisible(false);
                dispose();
            }
        });
    }

    private void mostrarURLInput(){
        String stringURL = JOptionPane.showInputDialog(null, null, "URL da imagem", JOptionPane.QUESTION_MESSAGE);
        if (stringURL != null){
            try {
                URL url = new URL(stringURL);
                imagem = ImageIO.read(url);
                if (imagem == null){
                    JOptionPane.showMessageDialog(null, "ERRO ao carregar imagem!!", "ERRO", JOptionPane.ERROR_MESSAGE);
                } else {
                    sucesso = true;
                    new GUI(imagem);
                }
            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(null, "URL invalido!!", "ERRO", JOptionPane.ERROR_MESSAGE);
                sucesso = false;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "ERRO ao carregar imagem", "ERRO", JOptionPane.ERROR_MESSAGE);
                sucesso = false;
            }
        }
    }

    private void mostrarBuscarInput() {
        JFrame novoJFrame = new JFrame();
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./imagens"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));

        int result = fileChooser.showOpenDialog(novoJFrame);

        if (result == JFileChooser.APPROVE_OPTION){
            try {
                imagem = ImageIO.read(fileChooser.getSelectedFile());
                sucesso = true;
                new GUI(imagem);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "ERRO ao ler arquivo!!", "ERRO", JOptionPane.ERROR_MESSAGE);
                sucesso = false;
            }
        } else sucesso = false;
    }
}

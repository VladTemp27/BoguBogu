import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

public class HuffmanConverterApp {
    // Instance variables for GUI components and data processing
    private JFrame frame;
    private JPanel mainPanel;
    private JButton huffmanToTextButton;
    private JButton textToHuffmanButton;
    private JButton exitButton;
    private HuffmanGenerator huffmanGenerator = new HuffmanGenerator();
    private LinkedList<CustomNode>letterFrequency = null;
    private StringProcessor processor = new StringProcessor();
    String textToHCInput = null;
    private int noConversion;

    // Constructor for initializing the main GUI
    public HuffmanConverterApp() {
        frame = new JFrame("Huffman Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        huffmanToTextButton = new JButton("2. Huffman to Text Converter");
        textToHuffmanButton = new JButton("1. Text to Huffman Converter");
        exitButton = new JButton("Exit");

        mainPanel.add(textToHuffmanButton);
        mainPanel.add(huffmanToTextButton);
        mainPanel.add(exitButton);

        frame.add(mainPanel, BorderLayout.CENTER);

        // Action listeners for each button
        huffmanToTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayHuffmanToTextConverter(frame);
            }
        });

        textToHuffmanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTextToHuffmanConverter(frame);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete a file and exit the application
                File diagramFile = new File("MidProj2/src/tree.png");
                diagramFile.delete();
                System.exit(0);
            }
        });

        frame.setVisible(true);

    } // end of HuffmanConverterApp constructor

    // Method for displaying the Huffman to Text Converter GUI
    private void displayHuffmanToTextConverter(JFrame menuFrame) {
        // Get the location of the menu frame
        int x = menuFrame.getLocationOnScreen().x;
        int y = menuFrame.getLocationOnScreen().y;
        JFrame huffmanToTextFrame = new JFrame("Huffman to Text Converter");
        huffmanToTextFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        huffmanToTextFrame.setBounds(x,y,400, 150);
        menuFrame.setVisible(false);
        huffmanToTextFrame.setLayout(new BorderLayout());

        JPanel huffmanToTextPanel = new JPanel();
        huffmanToTextPanel.setLayout(new GridLayout(3, 1));

        // Components for the Huffman to Text Converter GUI
        JTextField huffmanCodeInput = new JTextField();
        JButton convertButton = new JButton("Convert");
        JButton backButton = new JButton("Back");

        huffmanToTextPanel.add(huffmanCodeInput);
        huffmanToTextPanel.add(convertButton);
        huffmanToTextPanel.add(backButton);

        huffmanToTextFrame.add(huffmanToTextPanel, BorderLayout.CENTER);

        // Action listener for the convert button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the letterFrequency is null
                if(letterFrequency==null){
                    JOptionPane.showMessageDialog(huffmanToTextFrame,"Please execute text to huffman first");
                }else {
                    // Decode the Huffman code and display the result
                    String huffmanToText = huffmanCodeInput.getText();
                    String decodedText = decodeHuffman(huffmanToText);
                    JOptionPane.showMessageDialog(huffmanToTextFrame, "Decoded Text: " + decodedText);
                }
            }
        });

        // Action listener for the back button
        backButton.addActionListener(back -> {
            int x1 = huffmanToTextFrame.getLocationOnScreen().x;
            int y1 = huffmanToTextFrame.getLocationOnScreen().y;
            menuFrame.setLocation(x1, y1);
            huffmanToTextFrame.setVisible(false);
            menuFrame.setVisible(true);
        });

        huffmanToTextFrame.setVisible(true);

    } // end of displayHuffmanToTextConverter method

    private void displayTextToHuffmanConverter(JFrame menuFrame) {
        int x = menuFrame.getLocationOnScreen().x;
        int y = menuFrame.getLocationOnScreen().y;
        JFrame textToHuffmanFrame = new JFrame("Text to Huffman Converter");
        textToHuffmanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textToHuffmanFrame.setBounds(x,y,400, 200); // Increased the frame height, and sets the location based on where the menu is
        menuFrame.setVisible(false);    // Hides the menu
        textToHuffmanFrame.setLayout(new BorderLayout());

        JPanel textToHuffmanPanel = new JPanel();
        textToHuffmanPanel.setLayout(new GridLayout(4, 1)); // Added one more row

        JTextField textInput = new JTextField();
        JButton convertButton = new JButton("Convert");
        JButton checkHuffmanButton = new JButton("Check Huffman Diagram"); // New button
        JButton backButton = new JButton("Back");

        textToHuffmanPanel.add(textInput);
        textToHuffmanPanel.add(convertButton);
        textToHuffmanPanel.add(checkHuffmanButton); // Added the new button
        textToHuffmanPanel.add(backButton);

        textToHuffmanFrame.add(textToHuffmanPanel, BorderLayout.CENTER);

        backButton.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent back){
               int x = textToHuffmanFrame.getLocationOnScreen().x;
               int y = textToHuffmanFrame.getLocationOnScreen().y;
               menuFrame.setLocation(x, y);
               textToHuffmanFrame.setVisible(false);
               menuFrame.setVisible(true);
           }
        });

        // Displays converted text to huffman
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noConversion += 1;
                File treeDiagram = new File("MidProj2/src/tree.png");
                treeDiagram.delete();
                textToHCInput = null; //
                textToHCInput = textInput.getText();
                letterFrequency = null;
                letterFrequency = processor.getFrequency(textToHCInput);
                String huffmanCode = encodeHuffman(textToHCInput, letterFrequency);
                String output = "Character | Huffman code | Number of Bits\n";
                output += "---------------------\n";
                output += huffmanCode+"\n";
                output += huffmanGenerator.memorySave(letterFrequency)+"\n";
                output += "Text to Huffman code representation:"+huffmanGenerator.textToHuffman(textToHCInput);
                treeDiagram.renameTo(new File("MidProj2/src/"+noConversion+".png"));
                JOptionPane.showMessageDialog(textToHuffmanFrame, output);
            }
        });

        checkHuffmanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your logic to check the Huffman tree representation here
                File diagramFile = new File("MidProj2/src/"+noConversion+".png");

                if(diagramFile.exists()) {
                    ImageIcon diagram = new ImageIcon("MidProj2/src/"+noConversion+".png");

                    JOptionPane.showMessageDialog(textToHuffmanFrame, diagram);
                }else{
                    JOptionPane.showMessageDialog(textToHuffmanFrame, "Please convert a text to huffman code first");
                }
                diagramFile.deleteOnExit();
            }
        });

        textToHuffmanFrame.setVisible(true);
    }

    // Huffman encoding function
    private String encodeHuffman(String text, LinkedList<CustomNode> letterFrequency) {
        // Add Huffman encoding logic here
        // build a Huffman tree and generate the Huffman codes for the characters in the input text.
        huffmanGenerator = new HuffmanGenerator();
        return huffmanGenerator.generateHuffmanCode(text,letterFrequency); // Replace with actual code
    }

    // Huffman decoding function
    private String decodeHuffman(String huffmanCode) {
        // Add Huffman decoding logic here
        // use the Huffman tree to decode the Huffman code back to the original text.

        return huffmanGenerator.huffmanToTextV2(huffmanCode,letterFrequency,textToHCInput); // Replace with actual code
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HuffmanConverterApp();
            }
        });
    }
}
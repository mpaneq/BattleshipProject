package panels;

import params.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel implements ActionListener {

    JTextField field2;
    JTextField field3;
    JTextField field4;
    JTextField field5;
    JTextField field6;
    JTextField field7;
    JTextField field10;
    JTextField field11;

    JPanel panel;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;
    JPanel panel6;
    JPanel panel7;
    JPanel panel8;
    JPanel panel9;

    JPanel panel10;
    JPanel panel11;
    JPanel panel12;
    JPanel panel13;

    JPanel buttonPanel;


    JRadioButton easyLevelButton;
    JRadioButton button2;
    ButtonGroup group;

    JButton startButton;

    JLabel label;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JLabel label5;
    JLabel label6;
    JLabel label7;
    JLabel label8;
    JLabel label9;
    JLabel label10;
    JLabel label11;
    JLabel label13;

    JFrame frame;

    private final int EASY_LEVEL = 0;
    private final int HARD_LEVEL = 1;

    public SettingsPanel() {
        createFrame();
        createComponents();
        addComponentsToFrame();
        addActionListiner();
        createFrameToVisible();
    }

    public void createComponents() {
        createLables();
        createTextFields();
        createPanels();
        createButtons();
    }

    public void addComponentsToFrame(){
        addComponentsToLabels();
        setFontToLabels();
        addButtonsToGroup();
        addButtonsToPanel();
        addComponentsToPanels();
        addPanelsToFrame();
    }

    public void createFrame() {
        frame = new JFrame();
        frame.setSize(450, 550);
        frame.setTitle("Gra w statki");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(14,1 ));
    }

    public void createLables() {
        label = new JLabel("Ustawienia");
        label2 = new JLabel("Szerokość mapy:                                 ");
        label3 = new JLabel("Ilość pancerników:                          ");
        label4 = new JLabel("Punktacja za trafienie:        ");
        label5 = new JLabel("Ilość krążowników:                        ");
        label6 = new JLabel("Punktacja za trafienie:           ");
        label7 = new JLabel("Ilość niszczycieli:                           ");
        label8 = new JLabel("Gra" );
        label9 = new JLabel("Wybór statków" );
        label10 = new JLabel("Ilość ruchów gracza w jednej rundzie:  ");
        label11 = new JLabel("Punktacja za trafienie:           ");
        label13 = new JLabel("Poziom trudności gry" );
    }

    public void createTextFields() {
        field2 = new JTextField("12");
        field3 = new JTextField("1");
        field4 = new JTextField("10");
        field5 = new JTextField("2");
        field6 = new JTextField("5");
        field7 = new JTextField("2");
        field10 = new JTextField("1");
        field11 = new JTextField("2");

        setDefaultColumns();
    }

    public void setDefaultColumns() {
        field2.setColumns(10);
        field3.setColumns(10);
        field4.setColumns(10);
        field5.setColumns(10);
        field6.setColumns(10);
        field7.setColumns(10);
        field10.setColumns(10);
        field11.setColumns(10);
    }

    public void createPanels() {
        panel = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        panel6 = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        panel9 = new JPanel();
        panel10 = new JPanel();
        panel11 = new JPanel();
        panel12 = new JPanel();
        panel13 = new JPanel();
        buttonPanel = new JPanel();


        setDefaultPanelsColor();
    }

    public void setDefaultPanelsColor() {
        panel.setBackground(Color.WHITE);
        panel2.setBackground(Color.WHITE);
        panel3.setBackground(Color.WHITE);
        panel4.setBackground(Color.WHITE);
        panel5.setBackground(Color.WHITE);
        panel6.setBackground(Color.WHITE);
        panel7.setBackground(Color.WHITE);
        panel8.setBackground(Color.WHITE);
        panel9.setBackground(Color.WHITE);
        panel10.setBackground(Color.WHITE);
        panel11.setBackground(Color.WHITE);
        panel12.setBackground(Color.WHITE);
        panel13.setBackground(Color.WHITE);
        buttonPanel.setBackground(Color.WHITE);

    }

    public void addPanelsToFrame() {
        frame.add(panel);
        frame.add(panel8);
        frame.add(panel2);
        frame.add(panel10);
        frame.add(panel9);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel6);
        frame.add(panel7);
        frame.add(panel11);
        frame.add(panel13);
        frame.add(panel12);
        frame.add(buttonPanel);
    }

    public void addComponentsToPanels() {
        panel.add(label);
        panel2.add(label2);
        panel2.add(field2);
        panel3.add(label3);
        panel3.add(field3);
        panel4.add(label4);
        panel4.add(field4);
        panel5.add(label5);
        panel5.add(field5);
        panel6.add(label6);
        panel6.add(field6);
        panel7.add(label7);
        panel7.add(field7);
        panel8.add(label8);
        panel9.add(label9);
        panel10.add(label10);
        panel10.add(field10);
        panel11.add(label11);
        panel11.add(field11);
        panel13.add(label13);

        buttonPanel.add(startButton);
    }

    public void addComponentsToLabels() {
        label2.setLabelFor(field2);
        label3.setLabelFor(field3);
        label.setLabelFor(field4);
        label10.setLabelFor(field10);
    }

    public void createFrameToVisible() { frame.setVisible(true); }


    public void createButtons() {
        easyLevelButton = new JRadioButton("Łatwy");
        button2 = new JRadioButton("Trudny");
        startButton = new JButton("Przejdz dalej");
        group = new ButtonGroup();
        setDefaultButton();
    }

    public void setDefaultButton() { easyLevelButton.setSelected(true); }

    public void addButtonsToGroup() {
        group.add(easyLevelButton);
        group.add(button2);
    }

    public void addButtonsToPanel() {
        panel12.add(easyLevelButton);
        panel12.add(button2);
    }

    public void setFontToLabels() {
        label.setFont(new Font("Courier", Font.BOLD,30));
        label8.setFont(new Font("Courier", Font.BOLD,20));
        label9.setFont(new Font("Courier", Font.BOLD,20));
        label13.setFont(new Font("Courier", Font.BOLD,20));
    }

    public void addActionListiner(){ startButton.addActionListener(this); }

    @Override
    public void actionPerformed(ActionEvent e) {
        int difficultyLevel = getDifficultyLevel();
        SettingsParams settingsParams = getSettingParams(difficultyLevel);
        frame.dispose();
        new PlayPanel(settingsParams);
    }
    
    public int getDifficultyLevel(){
        if(easyLevelButton.isSelected()){ return EASY_LEVEL; }
        else { return HARD_LEVEL; }
    }

    public void paint(Graphics g){
        ImageIcon icon = new ImageIcon("src/sea.jpeg");
        Image image = icon.getImage();
        g.drawImage(image,0,0,getWidth(), (getHeight() * 2 + 59), null);
    }

    public SettingsParams getSettingParams(int difficultyLevel){
        return new SettingsParams(
                Integer.parseInt(field2.getText()),
                Integer.parseInt(field10.getText()),
                Integer.parseInt(field3.getText()),
                Integer.parseInt(field4.getText()),
                Integer.parseInt(field5.getText()),
                Integer.parseInt(field6.getText()),
                Integer.parseInt(field7.getText()),
                Integer.parseInt(field11.getText()),
                difficultyLevel
        );
    }
}


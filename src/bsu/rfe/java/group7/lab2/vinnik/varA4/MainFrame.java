package bsu.rfe.java.group7.lab2.vinnik.varA4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import static java.lang.Math.*;

@SuppressWarnings("serial")

public class MainFrame extends JFrame {

	private static final int WIDTH = 1000;
	private static final int HEIGHT = 800;

	private JTextField textFieldX;
	private JTextField textFieldY; 
	private JTextField textFieldZ; 
	private JTextField textFieldResult; // Текстовое поле для отображения результата,как компонент, совместно используемый в различных методах
	private ButtonGroup radioButtons = new ButtonGroup(); // Группа радио-кнопок для обеспечения уникальности выделения в группе
	private Box hboxFormulaType = Box.createHorizontalBox(); // Контейнер для отображения радио-кнопок
	private Box hboxMemoryType = Box.createHorizontalBox(); // Контейнер для отображения кнопок работы с памятью
    Box imBox1 = Box.createHorizontalBox();
    Box imBox2 = Box.createHorizontalBox();
	private int formulaId = 1;
	
	public double calculate1(double x, double y, double z) {
		return Math.pow(Math.pow(Math.sin(y)+Math.pow(y, 2)+Math.exp(Math.cos(y)), 2)+Math.pow((Math.log(Math.pow(z, 2))+Math.sin(PI*Math.pow(x, 2))), 3), 1/2);
	}
	public Double calculate2(Double x, Double y, Double z) {
		
		double a = Math.pow(y, 1/2)*3*Math.pow(z, x);
		double b = Math.pow(1+Math.pow(y, 3), 1/2);
		return a/b;
	}
	
	private void addRadioButton(String buttonName, final int formulaId) { // Вспомогательный метод для добавления кнопок на панель
		JRadioButton button = new JRadioButton(buttonName);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				MainFrame.this.formulaId = formulaId;
				switch(formulaId) {
				case 1: {
					imBox1.setVisible(true);
					imBox2.setVisible(false);
					break;
				}
				case 2: {
					imBox2.setVisible(true);
					imBox1.setVisible(false);
					break;
				}
				}
			}
		});
		radioButtons.add(button);
		hboxFormulaType.add(button);
	}
	
	public MainFrame() {
		super("Вычисление формулы");
		
		JLabel textFieldMemoryData1 = new JLabel("0");
		
		setSize(WIDTH, HEIGHT);
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		setLocation((kit.getScreenSize().width - WIDTH)/2,(kit.getScreenSize().height - HEIGHT)/2); // Отцентрировать окно приложения на экране
		
        JButton buttonMemoryPlus = new JButton("M+");
        buttonMemoryPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Double newValue = Double.parseDouble(textFieldResult.getText()) + Double.parseDouble(textFieldMemoryData1.getText());
            	textFieldMemoryData1.setText(newValue.toString());
            }
        });

        JButton buttonMemoryClear = new JButton("MC");
        buttonMemoryClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		textFieldMemoryData1.setText("0");
            }
        });
        
        // Коробка кнопок с выбором формулы
        hboxFormulaType.add(Box.createHorizontalGlue());
		addRadioButton("Формула 1", 1);
		addRadioButton("Формула 2", 2);

		radioButtons.setSelected(radioButtons.getElements().nextElement().getModel(), true);

		hboxFormulaType.add(Box.createHorizontalGlue());
		hboxFormulaType.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

		
		// Коробка памяти
		Box hboxMemory = Box.createHorizontalBox();
		hboxMemory.add(Box.createHorizontalGlue());
        hboxMemory.add(textFieldMemoryData1);
        hboxMemory.add(Box.createHorizontalStrut(50));
        hboxMemory.add(buttonMemoryPlus);
        hboxMemory.add(buttonMemoryClear);
        hboxMemory.add(Box.createHorizontalGlue());
		
		// Создать область с полями ввода для X и Y

		JLabel labelForX = new JLabel("X:");
		textFieldX = new JTextField("0", 10);
		textFieldX.setMaximumSize(textFieldX.getPreferredSize());
		JLabel labelForY = new JLabel("Y:");
		textFieldY = new JTextField("0", 10);
		textFieldY.setMaximumSize(textFieldY.getPreferredSize());
		JLabel labelForZ = new JLabel("Z:");
		textFieldZ = new JTextField("0", 10);
		textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());
		
		Box hboxVariables = Box.createHorizontalBox();
		hboxVariables.setBorder(BorderFactory.createLineBorder(Color.RED));
		hboxVariables.add(labelForX);
		hboxVariables.add(Box.createHorizontalStrut(10));
		hboxVariables.add(textFieldX);
		hboxVariables.add(Box.createHorizontalGlue());
		hboxVariables.add(labelForY);
		hboxVariables.add(Box.createHorizontalStrut(10));
		hboxVariables.add(textFieldY);
		hboxVariables.add(Box.createHorizontalGlue());
		hboxVariables.add(labelForZ);
		hboxVariables.add(Box.createHorizontalStrut(10));
		hboxVariables.add(textFieldZ);

		
		// Создать область для вывода результата
		JLabel labelForResult = new JLabel("Результат:");
		textFieldResult = new JTextField("0", 10);
		textFieldResult.setMaximumSize(textFieldResult.getPreferredSize());
		Box hboxResult = Box.createHorizontalBox();
		hboxResult.add(Box.createHorizontalGlue());
		hboxResult.add(labelForResult);
		hboxResult.add(Box.createHorizontalStrut(10));
		hboxResult.add(textFieldResult);
		hboxResult.add(Box.createHorizontalGlue());
		hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		// Создать область для кнопок
		JButton buttonCalc = new JButton("Вычислить");
		buttonCalc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					Double x = Double.parseDouble(textFieldX.getText());
					Double y = Double.parseDouble(textFieldY.getText());
					Double z = Double.parseDouble(textFieldZ.getText());
					Double result;
					if (formulaId==2)
						result = calculate2(x, y,z);
					else
						result = calculate1(x, y,z);
					textFieldResult.setText(result.toString());
				} 
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(MainFrame.this,
					"Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
					JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		JButton buttonReset = new JButton("Очистить поля");
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				textFieldX.setText("0");
				textFieldY.setText("0");
				textFieldZ.setText("0");
				textFieldResult.setText("0");
			}
		});
		
		Box hboxButtons = Box.createHorizontalBox();
		hboxButtons.add(Box.createHorizontalGlue());
		hboxButtons.add(buttonCalc);
		hboxButtons.add(Box.createHorizontalStrut(30));
		hboxButtons.add(buttonReset);
		hboxButtons.add(Box.createHorizontalGlue());
		hboxButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		
		// Связать области воедино в компоновке BoxLayout
		Box contentBox = Box.createVerticalBox();
		contentBox.add(hboxVariables);
		contentBox.add(hboxResult);
		contentBox.add(hboxMemoryType);
		contentBox.add(hboxMemory);
		contentBox.add(hboxButtons);
		contentBox.add(hboxFormulaType);
		getContentPane().add(contentBox, BorderLayout.CENTER);
	}

	
	public static void main(String[] args) {
		double	x=22;
		double y=22;
		double z=22;
		double res=Math.pow(Math.pow(Math.sin(y)+Math.pow(y, 2)+Math.exp(Math.cos(y)), 2)+Math.pow((Math.log(Math.pow(z, 2))+Math.sin(PI*Math.pow(x, 2))), 3), 1/2);
		System.out.println(res);
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

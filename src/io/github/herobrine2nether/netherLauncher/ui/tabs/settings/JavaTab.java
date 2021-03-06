package io.github.herobrine2nether.netherLauncher.ui.tabs.settings;

import io.github.herobrine2nether.netherLauncher.backend.Util.Logging;
import io.github.herobrine2nether.netherLauncher.backend.files.Assets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class JavaTab extends JPanel {
    String ramValue = "1";
    String ramType = "G";
    String[] ramTypes = new String[] {"M", "G"};
    JTextField ramValueBox = new JTextField(ramValue);
    JComboBox<String> ramTypeSelector = new JComboBox<>(ramTypes);
    public JavaTab() {
        ramValueBox.setColumns(4);
        JLabel ramLabel = new JLabel("Set RAM for Minecraft");
        add(ramLabel);
        add(ramValueBox);
        add(ramTypeSelector);

        ramValueBox.setText(readValue());
        ramTypeSelector.setSelectedItem(readType());

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                save();
                String temp = readValue();
            }
        });
        add(saveBtn);
    }

    public void save() {
        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(Assets.RAM.getPath()), "utf-8"))) {
                writer.write(ramValueBox.getText() + ramTypeSelector.getSelectedItem());
                Logging.Log("Saved", 0);
            }
        } catch (IOException e) {
            Logging.Log("Problem Saving Settings", 2);
            Logging.Log(e.getMessage(), 3);
            e.printStackTrace();
        }
    }

    public String readValue() {
        BufferedReader br = null;
        try {
            String line;

            br = new BufferedReader(new FileReader(Assets.RAM.getPath()));

            if ((line = br.readLine()) != null) {
                Logging.Log("Found RAM Settings as " + line, 0);
                int i = 0;
                if (line.contains("M")) {
                    i = line.indexOf("M");
                }
                if (line.contains("G")) {
                    i = line.indexOf("G");
                }
                String amount = line.substring(0, i);
                return amount;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "1";
    }

    public String readType() {
        BufferedReader br = null;
        try {
            String line;

            br = new BufferedReader(new FileReader(Assets.RAM.getPath()));

            if ((line = br.readLine()) != null) {
                //Logging.Log("Found RAM Settings as " + line, 0);
                int i = 0;
                if (line.contains("M")) {
                    i = line.indexOf("M");
                }
                if (line.contains("G")) {
                    i = line.indexOf("G");
                }
                String type = line.substring(i, i + 1);
                return type;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "G";
    }
}

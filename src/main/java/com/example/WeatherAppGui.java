package com.example;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import org.json.simple.JSONObject;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;

    public WeatherAppGui() {
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents() {
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        JLabel weatherConditionImage = new JLabel(loadImage("cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("25°C");
        temperatureText.setBounds(0, 350, 450, 48);
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        add(temperatureText);

        JLabel weatherConditionDescription = new JLabel("Cloudy");
        weatherConditionDescription.setBounds(0, 398, 450, 48);
        weatherConditionDescription.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDescription.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDescription);
        
        JLabel humidityImage = new JLabel(loadImage("humidity.png"));
        humidityImage.setBounds(15, 500, 70, 66);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b></html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(humidityText);

        JLabel windImage = new JLabel(loadImage("windspeed.png"));
        windImage.setBounds(220, 500, 70, 66);
        add(windImage);

        JLabel windText = new JLabel("<html><b>Wind Speed</b> 15km/h</html>");
        windText.setBounds(300, 500, 110, 55);
        windText.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(windText);

        JButton searchButton = new JButton(loadImage("search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(360, 15, 50, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();
                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);
                if (weatherData != null) {
                    String weatherCondition = (String) weatherData.get("weather_condition");
                    switch (weatherCondition) {
                        case "Clear":
                            weatherConditionImage.setIcon(loadImage("clear.png"));
                            break;
                        case "Cloudy":
                            weatherConditionImage.setIcon(loadImage("cloudy.png"));
                            break;
                        case "Rain":
                            weatherConditionImage.setIcon(loadImage("rain.png"));
                            break;
                        case "Snow":
                            weatherConditionImage.setIcon(loadImage("snow.png"));
                            break;
                    }

                    double temperature = (double) weatherData.get("temperature");
                    temperatureText.setText(temperature + " °C");
                    weatherConditionDescription.setText(weatherCondition);
                    long humidity = (long) weatherData.get("humidity");
                    humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");
                    double windspeed = (double) weatherData.get("wind_speed");
                    windText.setText("<html><b>Wind Speed</b> " + windspeed + " km/h</html>");
                } else {
                    System.out.println("Failed to retrieve weather data.");
                }
            }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String resourcePath) {
        try {

            URL urlImages =  getClass().getResource(resourcePath);
            BufferedImage image = ImageIO.read(urlImages);
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Could not find resource");
        return null;
    }

}

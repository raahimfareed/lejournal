package Views;

import Components.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import Components.View;
import Models.CalendarData;
import com.formdev.flatlaf.FlatIntelliJLaf;
import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Calendar;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CalendarApp extends View {
    private JLabel monthLabel;
    private JPanel calendarPanel;
    private JTextField[][] dateFields;
    private JTextArea[][] todoFields;
    private int currentYear;
    private int currentMonth;
    private int selectedRow;
    private int selectedColumn;

    private String[] months = {
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    };

    public CalendarApp() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        monthLabel = new JLabel();
        monthLabel.setFont(new Font("Arial", Font.BOLD, 18));
        monthLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton prevButton = new JButton("Previous");
        JButton insert = new JButton("Insert");
        JButton nextButton = new JButton("Next");

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeMonth(-1);
            }
        });

        insert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertTodo();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeMonth(1);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(insert);
        buttonPanel.add(nextButton);

        calendarPanel = new JPanel(new GridLayout(6, 6));

        dateFields = new JTextField[6][6];
        todoFields = new JTextArea[6][6];

        UIManager.put("TextField.background", new Color(40, 40, 40));
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextField.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

        UIManager.put("TextArea.background", new Color(60, 60, 60));
        UIManager.put("TextArea.foreground", Color.WHITE);
        UIManager.put("TextArea.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));
        calendarPanel.setBackground(new Color(30, 30, 30));

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                dateFields[i][j] = new JTextField();
                dateFields[i][j].setEditable(false);
                dateFields[i][j].setBackground(Color.WHITE);

                todoFields[i][j] = new JTextArea();
                todoFields[i][j].setLineWrap(true);
                todoFields[i][j].setWrapStyleWord(true);

                JPanel cellPanel = new JPanel(new BorderLayout());
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                cellPanel.add(dateFields[i][j], BorderLayout.NORTH);
                cellPanel.add(todoFields[i][j], BorderLayout.CENTER);

                calendarPanel.add(cellPanel);

                int finalI = i;
                int finalJ = j;
                todoFields[i][j].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        selectedRow = finalI;
                        selectedColumn = finalJ;
                    }
                });
            }
        }

        Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH) + 1;

        updateCalendar(currentYear, currentMonth);

        add(monthLabel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateCalendar(int year, int month) {
        monthLabel.setText(months[month - 1] + " " + year);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);

        int startDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Get the to-do data from the database for the specified year and month
        try (Session session = sessionFactory.openSession()) {
            int startDate = year * 10000 + month * 100 + 1;
            int endDate = year * 10000 + month * 100 + maxDays;

            Query<CalendarData> query = session.createQuery("FROM CalendarData WHERE date >= :startDate AND date <= :endDate", CalendarData.class);
            ((Query<?>) query).setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);

            List<CalendarData> TodoData = query.list();

            // Clear the existing data in the GUI
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    dateFields[i][j].setText("");
                    todoFields[i][j].setText("");
                }
            }

            // Display the dates and to-do data
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (i == 0 && j < startDay) {
                        // Empty cells before the first day of the month
                        continue;
                    }

                    int day = i * 6 + j - startDay + 1;
                    if (day > maxDays) {
                        // Cells after the last day of the month
                        continue;
                    }

                    dateFields[i][j].setText(String.valueOf(day));

                    // Check if there is to-do data for the current date
                    for (CalendarData data : TodoData) {
                        if (data.getDate() == year * 10000 + month * 100 + day) {
                            todoFields[i][j].setText(data.getTodo());
                            break;
                        }
                    }
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            // Handle any exceptions that occur during Hibernate operations
        }
    }


    private void changeMonth(int value) {
        currentMonth += value;

        if (currentMonth < 1) {
            currentMonth = 12;
            currentYear--;
        } else if (currentMonth > 12) {
            currentMonth = 1;
            currentYear++;
        }

        updateCalendar(currentYear, currentMonth);
    }

    private void insertTodo() {
        String todo = todoFields[selectedRow][selectedColumn].getText();
        int day = Integer.parseInt(dateFields[selectedRow][selectedColumn].getText());
        int date = currentYear * 10000 + currentMonth * 100 + day;
        // Create a CalendarData object with the selected date and todo
        CalendarData calendarData = new CalendarData(date, todo);
        // Save the data using Hibernate
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(calendarData);
        transaction.commit();
        session.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                } catch (Exception e) {
                    // Handle theme setting exception
                }

                JFrame frame = new JFrame("Calendar App");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new CalendarApp());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}

package Unknown;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Contact {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private DefaultListModel<String> contactListModel;
    private JList<String> contactList;
    private JTextField nameField, phoneField, emailField;
    private JLabel nameLabel, phoneLabel, emailLabel;
    private ArrayList<ContactInfo> contacts;

    public Contact() {
        frame = new JFrame("Contact Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        contacts = new ArrayList<>();

        createContactListView();
        createContactDetailsView();
        createContactFormView();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void createContactListView() {
        JPanel listPanel = new JPanel(new BorderLayout());
        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);

        JButton addButton = new JButton("Add New Contact");
        JButton viewButton = new JButton("View Details");

        addButton.addActionListener(e -> cardLayout.show(mainPanel, "AddContact"));
        viewButton.addActionListener(e -> showContactDetails());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        listPanel.add(new JScrollPane(contactList), BorderLayout.CENTER);
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(listPanel, "ContactList");
    }

    private void createContactDetailsView() {
        JPanel detailsPanel = new JPanel(new GridLayout(4, 1));
        nameLabel = new JLabel();
        phoneLabel = new JLabel();
        emailLabel = new JLabel();
        JButton backButton = new JButton("Back to List");

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ContactList"));

        detailsPanel.add(nameLabel);
        detailsPanel.add(phoneLabel);
        detailsPanel.add(emailLabel);
        detailsPanel.add(backButton);

        mainPanel.add(detailsPanel, "ContactDetails");
    }

    private void createContactFormView() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        JButton saveButton = new JButton("Save Contact");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> saveContact());
        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "ContactList"));

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(saveButton);
        formPanel.add(cancelButton);

        mainPanel.add(formPanel, "AddContact");
    }

    private void saveContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled!");
            return;
        }

        contacts.add(new ContactInfo(name, phone, email));
        contactListModel.addElement(name);
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");

        cardLayout.show(mainPanel, "ContactList");
    }

    private void showContactDetails() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a contact!");
            return;
        }

        ContactInfo selectedContact = contacts.get(selectedIndex);
        nameLabel.setText("Name: " + selectedContact.name);
        phoneLabel.setText("Phone: " + selectedContact.phone);
        emailLabel.setText("Email: " + selectedContact.email);

        cardLayout.show(mainPanel, "ContactDetails");
    }

    class ContactInfo {
        String name, phone, email;
        public ContactInfo(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }
    }
}


package vs.hashtoclipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;

public class InputSceneControler {

    @FXML private PasswordField inputText;
    @FXML private Button okButton;
    @FXML private CheckBox toUpperCheckbox;

    @FXML
    void initialize() {
    	okButton.setOnAction(e -> performAction(e));
    }

	private void performAction(ActionEvent e) {
		try {
			String out = byteArrayToHexString(toSHA1(inputText.getText()));
			if(toUpperCheckbox.isSelected()) {
				out = out.toUpperCase();
			}
			System.out.println(out);
			copyToClipboard(out);
			inputText.selectAll();
		} catch (GeneralSecurityException e1) {
			e1.printStackTrace();
		}
	}

	private static void copyToClipboard(String out) {
		StringSelection selection = new StringSelection(out);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	}

	private byte[] toSHA1(String in) throws GeneralSecurityException {
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-1");
		return md.digest(in.getBytes());
	}

	private String byteArrayToHexString(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			result.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}
}

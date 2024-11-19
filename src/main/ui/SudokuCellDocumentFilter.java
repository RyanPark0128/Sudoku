package ui;
import javax.swing.text.*;
import java.awt.*;

class SudokuCellDocumentFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (isValidSudokuInput(string, fb.getDocument().getLength())) {
            super.insertString(fb, offset, string, attr);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (isValidSudokuInput(text, fb.getDocument().getLength() - length + text.length())) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private boolean isValidSudokuInput(String text, int totalLength) {
        if (totalLength > 1) {
            return false; // Limit input to a single character
        }
        return text.matches("[1-9]?"); // Accepts digits 1-9 or empty string
    }
}
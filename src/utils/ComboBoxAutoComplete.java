package utils;

import com.smohtadi.finView.services.IService;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ComboBoxAutoComplete extends ComboBox<String> {
  private List<String> dbQuery;
  private IService service;
  private String prev = "";

  public ComboBoxAutoComplete(IService service) {
    super();
    this.setVisibleRowCount(10);
    this.setEditable(true);
    this.service = service;

    this.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
      textChangeListener(newValue);
    });
  }

  public void setLayout(Pane parent, int x, int y) {
    parent.getChildren().add(this);
    this.setLayoutX(x);
    this.setLayoutY(y);
  }

  private void addSuggestionsToComboBox(String prefix) {
    if (dbQuery == null) { return; }
    List<String> result = new ArrayList<>();
    for (String s : dbQuery) {
      if (s.startsWith(prefix))
        result.add(s);
    }
    this.getItems().setAll(result);
  }

  private void filterSuggestionsFromComboBox(String prefix) {
    int n = this.getItems().size();
    List<String> cbList = this.getItems();
    List<String> newList = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (cbList.get(i).startsWith(prefix)) {
        newList.add(cbList.get(i));
      }
    }
    this.getItems().setAll(newList);
  }

  private void textChangeListener(String newValue) {
    if (newValue.length() == 2) {
      if (!newValue.equals(prev)) {
        this.dbQuery = this.service.fetchSuggestions(newValue);
        this.prev = newValue;
      }
      addSuggestionsToComboBox(newValue.toUpperCase());
      this.show();
    } else if (newValue.length() > 2) {
      filterSuggestionsFromComboBox(newValue.toUpperCase());
      this.show();
    } else {
      this.hide();
    }
  }
}

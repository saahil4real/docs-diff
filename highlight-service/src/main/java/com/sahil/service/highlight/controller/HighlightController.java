package com.sahil.service.highlight.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.service.highlight.model.DiffList;
import com.sahil.service.highlight.model.HighlightRequest;
import com.sahil.service.highlight.model.OldNewDocuments;
import com.sahil.service.highlight.model.Operation;
import com.sahil.service.highlight.util.TextHighlighter;

@RestController
public class HighlightController {

  @PostMapping("/highlight")
  public void highlight(@RequestBody HighlightRequest highlightRequest) {
    OldNewDocuments oldNewDocuments = highlightRequest.getOldNewDocuments();
    DiffList diffList = highlightRequest.getDiffList();
    TextHighlighter oldTextHighlighter = null, newTextHighlighter = null;
    try {
      oldTextHighlighter = new TextHighlighter(oldNewDocuments.getOldFile(),
          oldNewDocuments.getOldFileHighlighted(), diffList, Operation.DELETE, false);
      newTextHighlighter = new TextHighlighter(oldNewDocuments.getNewFile(),
          oldNewDocuments.getNewFileHighlighted(), diffList, Operation.INSERT, false);
    } catch (IOException e) {
      e.getMessage();
    } finally {
      if (oldTextHighlighter != null && newTextHighlighter != null) {
        oldTextHighlighter.close();
        newTextHighlighter.close();
      }
    }
  }

}

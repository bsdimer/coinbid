var $textBox;

$(document).ready(function() {
    $textBox = $("textarea[id$='templateEditor']");

    function saveSelection(){
        $textBox.data("lastSelection", $textBox.getSelection());
    }

    $textBox.focusout(saveSelection);

    $textBox.bind("beforedeactivate", function() {
        saveSelection();
        $textBox.unbind("focusout");
    });
});

function insertText(text) {
    var selection = $textBox.data("lastSelection");
    if(typeof selection === 'undefined') {
        $textBox = $("textarea[id$='templateEditor']");
        selection = $textBox.getSelection();
    }
    $textBox.focus();
    $textBox.setSelection(selection.start, selection.end);
    $textBox.replaceSelectedText(text);
}


function registerPlaceholderClickEvent() {
    $("div[id$='placeholdersMultiSelectListbox'] li").click(
        function() {
            var data = $(this).attr("data-value");
            if(data != '') {
                insertText('${' + data + '}');
            }
        }
    );
}


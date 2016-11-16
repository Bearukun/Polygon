$(function() {
  $('#adresse').dawaautocomplete({
    select: function(event, data) {
      $('#adresse-choice').text(data.tekst);
    }
  });
});
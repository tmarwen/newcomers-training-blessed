/**
 * Created by The eXo Platform MEA
 * Author : Marwen Trabelsi
 * marwen.trabelsi.insat@gmail.com
 * November 26, 2014
 */
(function ($) {

  var listEventsURL = "/rest/events-manager/list";
  var removeEventURL = "/rest/events-manager/remove/:name";

  var $eventsTable = $('table#events-table');

  if ($eventsTable){
    $.getJSON(listEventsURL, function(data){

      var events = data;

      // Remove all event items first
      $("table#events-table tr").not("#events-table-header").remove();

      $.each(events,function(key,val){
        var eventName = val.name;
        var eventDate = val.date;
        var year = eventDate.year;
        var month = eventDate.year;
        var dayOfMonth = eventDate.dayOfMonth;
        var hourOfDay = eventDate.hourOfDay;
        var minute = eventDate.minute;
        var second = eventDate.second;

        var $eventRow = $("<tr></tr>").attr({
              id:eventName.replace(/\s/g,"")
            }).addClass("event-item");

        var $eventNameCol = $("<td></td>").addClass("eventItemName");

        $("<span></span>",{text:eventName}).appendTo($eventNameCol);

        // Append event name to trash icon so we can use it on remove call
        var $deleteLink = $("<a></a>").data({eventName:eventName}).addClass("eventDeleteIcon").appendTo($eventNameCol);
        $("<i></i>").addClass("uiIconTrash uiIconDarkGray").appendTo($deleteLink);

        $eventNameCol.appendTo($eventRow);

        $("<td></td>",{text:year}).appendTo($eventRow);
        $("<td></td>",{text:month}).appendTo($eventRow);
        $("<td></td>",{text:dayOfMonth}).appendTo($eventRow);
        $("<td></td>",{text:hourOfDay}).appendTo($eventRow);
        $("<td></td>",{text:minute}).appendTo($eventRow);

        $("<td></td>",{text:second}).appendTo($eventRow);
        $eventRow.appendTo($eventsTable);

      });
    });
  }

  $('#events-table').on('click', 'a.eventDeleteIcon', function () {

    if(confirm('You wish to delete this event?')){
      var eventName = $(this).data('eventName');
      var removeURL = removeEventURL.replace(':name',eventName);

      $.get(removeURL).done(function(){
        $('#events-table').find("tr#"+eventName.replace(/\s/g,"")).fadeOut(300,function(){
              $(this).remove();
            });
      });
    }
  });

})(jQuery);
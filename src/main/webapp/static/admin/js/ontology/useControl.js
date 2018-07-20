/*$("use-control").click(function(){
    alert(this);
    var words =this.attr("name");
    var status = this.attr("value");
    $.ajax({
        url: 'ontology/control/set',
        dataType: 'json',
        type: 'get',
        data: {'words':words,'status':status},
        success: function (result) {

            console.log(result);
        }
    })
});*/
function control(thisElement) {
    var words =thisElement.attr("name");
    var status = thisElement.attr("value");
    $.ajax({
        url: 'ontology/control/set',
        dataType: 'json',
        type: 'get',
        data: {'words':words,'status':status},
        success: function (result) {
            alert(result.status)
        }
})
}
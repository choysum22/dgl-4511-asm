//export table as excel
function exportTableToExcel(tableID, filename = "") {

    filename += "_booking_" + date();
    var selected_type = 1;
    /* Get the HTML data using Element by Id */
    var table = document.getElementById(tableID);

    /* Declaring array variable */
    var rows = [];
    var cells = [];

    //iterate through rows of table
    for (var i = 0, row; (row = table.rows[i]); i++) {
        //rows would be accessed using the "row" variable assigned in the for loop
        //Get each cell value/column from the row
        cells = [];

        for (var j = 0, col; (col = row.cells[j]); j++) {
            //cells would be accessed using the "col" variable assigned in the for loop
            //add each cell's data to the cells array

            cells.push(col.innerText);

        }

        /* add a new records in the array */
        rows.push(cells);
    }
    csvContent = "data:text/csv;charset=utf-8,";
    /* add the column delimiter as comma(,) and each row splitted by new line character (\n) */
    rows.forEach(function (rowArray) {
        row = rowArray.join(",");
        csvContent += row + "\r\n";
    });

    /* create a hidden <a> DOM node and set its download attribute */
    var encodedUri = encodeURI(csvContent);
    var link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    link.setAttribute("download", filename + ".csv");
    document.body.appendChild(link);
    /* download the data file named "Stock_Price_Report.csv" */
    link.click();
}

// get date as file name
function date() {
    var d = new Date();
    var month = ('0' + (d.getMonth() + 1)).slice(-2);
    var day = ('0' + d.getDate()).slice(-2);
    var year = d.getFullYear();
    var date = year + "" + month + "" + day;
    return date;
}

// Load the Visualization API and the piechart package.
google.load('visualization', '1.0', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.setOnLoadCallback(drawChart);


// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawChart() {
  drawBarComparison();
  drawLineComparison();
}


/**
 *  Popularity timeline displays the number of tweets mentioning
 *  the specified brand / product over time.
**/
function drawBarComparison () {
  // Create the data table.
  var data = new google.visualization.DataTable();
  data.addColumn('string', 'Brand');
  data.addColumn('number', 'Popularity');
  data.addColumn('number', 'Sentiment');
  data.addRows([
    ['Coca cola',   (200 / 411), 0.3],
    ['Pepsi',       (154 / 411), 0.1],
    ['Dr. Pepper',  (411 / 411), -0.2],
    ['Fanta',       (16  / 411),  -0.5]
  ]);

  // Set chart options
  var options = {
    title:  'Relative brand popularity and sentiment:',
    backgroundColor:  { fill:     'transparent' },
    vAxis:            { minValue: 0             },
    chartArea:        { width:    '60%'         }
  };

  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.BarChart(document.getElementById('chart_comparison_bar'));
  chart.draw(data, options);
}


/**
 *  Popularity timeline displays the number of tweets mentioning
 *  the specified brand / product over time.
**/
function drawLineComparison () {
  // Create the data table.
  var data = new google.visualization.DataTable();
  data.addColumn('date', 'Year');
  data.addColumn('number', 'Coca cola');
  data.addColumn('number', 'Pepsi');
  data.addColumn('number', 'Fanta');
  data.addColumn('number', 'Dr. Pepper');
  data.addRows([
    [new Date(2008, 0, 1), 0.1,  0.2, 0.3, -0.2],
    [new Date(2009, 0, 1), 0.3,  0.3, 0.5,  0.2],
    [new Date(2010, 0, 1), -0.1, 0.1, 0.8,  0.3],
    [new Date(2011, 0, 1), -0.7, 0.2, 0.9,  0.5],
    [new Date(2012, 0, 1), -0.5, 0.4, 0.8,  0.4]
  ]);

  // Set chart options
  var options = {
    title:  'Relative brand popularity and sentiment:',
    backgroundColor:  { fill:     'transparent' },
    vAxis:            { minValue: -1.0,
                        maxValue: 1.0           },
    chartArea:        { width:    '60%'         }
  };

  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.LineChart(document.getElementById('chart_comparison_line'));
  chart.draw(data, options);
}
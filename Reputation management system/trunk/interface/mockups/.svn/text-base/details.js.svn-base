// Load the Visualization API and the piechart package.
google.load('visualization', '1.0', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.setOnLoadCallback(drawChart);


// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawChart() {
  drawSentimentBreakdown();
  drawSentimentTimeline();
  drawPopularityTimeline();
}


/**
 *  Sentiment breakdown is a pie chart displaying the total
 *  number of positive, negative and neutral tweets.
**/
function drawSentimentBreakdown () {
  // Create the data table.
  var data = new google.visualization.DataTable();
  data.addColumn('string', 'Sentiment');
  data.addColumn('number', 'Tweets');
  data.addRows([
    ['Positive', 3513],
    ['Negative', 2232],
    ['Neutral',  13123]
  ]);

  // Set chart options
  var options = {
    title:'Sentiment breakdown by tweets:',
    backgroundColor:  { fill:     'transparent' }
  };

  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.PieChart(document.getElementById('chart_breakdown'));
  chart.draw(data, options);
}


/**
 *  Sentiment timeline is a line graph displaying the overall sentiment
 *  of a brand / product over time.
**/
function drawSentimentTimeline () {
  // Create the data table.
  var data = new google.visualization.DataTable();
  data.addColumn('date', 'Date');
  data.addColumn('number',   'Sentiment');
  data.addRows([
    [new Date(2012, 00, 01), 0.1],
    [new Date(2012, 00, 15), -0.2],
    [new Date(2012, 01, 01), 0.2],
    [new Date(2012, 01, 15), 0.4],
    [new Date(2012, 02, 01),  -0.3]
  ]);

  // Set chart options
  var options = {
    title:  'Overall sentiment timeline:',
    backgroundColor:  { fill:     'transparent' },
    vAxis:            { minValue: -1.0,
                        maxValue: 1.0           },
    legend:           { position: 'none'        }
  };

  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.LineChart(document.getElementById('chart_sentiment_timeline'));
  chart.draw(data, options);
}


/**
 *  Popularity timeline displays the number of tweets mentioning
 *  the specified brand / product over time.
**/
function drawPopularityTimeline () {
  // Create the data table.
  var data = new google.visualization.DataTable();
  data.addColumn('date', 'Date');
  data.addColumn('number',   'Tweets');
  data.addRows([
    [new Date(2012, 00, 01), 123],
    [new Date(2012, 00, 15), 250],
    [new Date(2012, 01, 01), 198],
    [new Date(2012, 01, 15), 110],
    [new Date(2012, 02, 01), 50]
  ]);

  // Set chart options
  var options = {
    title:  'Popularity Timeline:',
    backgroundColor:  { fill:     'transparent' },
    vAxis:            { minValue: 0             },
    legend:           { position: 'none'        }
  };

  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.LineChart(document.getElementById('chart_popularity_timeline'));
  chart.draw(data, options);
}
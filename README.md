# WChart
This component is used to display various types of graphs.

- [Custom range on Histogram](#custom-range-on-histogram)
- [Events](#events)
- [Handler Methods](#handler-methods)
- [Methods](#methods)

## Custom range on Histogram

When it is necessary to customize the range of the Y axis

You must configure on WChartSeries, folder Properties (new):
- showLastTickOnDataOverflow: the maximum value of the data as the last interval displayed on the Y axis, if you use showLastTickOnDataOverflow with true.
- minValueAxisY: the lower value you want to display on the Y axis
- maxValueAxisY: the maximum value you want to display on Y axis
- yTickInterval: the range value you want to display, example, if you set 30 in this property, it will display values between the minimum e maximum, if you use minValueAxisY as 0, maxValueAxisY as 90, in this example it will display label on Y axis to values 30 and 60. Also the values of minimum (0) and maximum (90) of this example.

## Events

### onLoad
This event is fired when the component is load. It passes nothing as parameters.

### onClick
This event is fired when there's a click on the data point of the current chart, the data point can be a point, a bar, a pie piece or others. The parameters that this event passes are the following:

```
name, attribute_y, value_y, attribute_x, value_x
```

### onDoubleClick

    This event is fired before the user click two times on the TableChart's line

#### :bookmark_tabs: Parameters
**schematic:** _(Object)_  Schematic handler object <br>
**handler:** _(Object)_ Component handler <br>
**event:** _(Object)_ Event is a object build to framework. And each chart have specific values. <br>

#### :pencil2: Example
```javascript
import { Controller, Inject } from '@philips/odin-ext';
import { WChart } from 'odin-utils/controllers';

@Controller({ domain: 'pfcs/PfcsPA', code: 11111 })
export default class ChartTest extends WChart {

  @Inject('DialogBoxType')
  DialogBoxType;

  @Inject('tasyWdialogbox')
  tasyWdialogbox;

  onDoubleClick(schematics, handler, event) {
    const conf = {
      type: this.DialogBoxType.CONFIRM,
      message: 22222, // Would you like to render the chart?
      defaultShow: 'BOTH',
      cancelButtonCode: 33333, // Cancel
      okButtonCode: 44444 // Ok
    };

    this.tasyWdialogbox(conf).then(() => {
      handler.renderChart();
    });
  }
}
```

#### :paperclip: Note

    If you would like some specific value brought by the sql registered in the sql field of wchart record or the wchart serie's code. You can get it as follows:
```javascript
import { Controller, Inject } from '@philips/odin-ext';
import { WChart } from 'odin-utils/controllers';

@Controller({ domain: 'pfcs/PfcsPA', code: 11111 })
export default class ChartTest extends WChart {

  @Inject('externalAccessManager')
  externalAccessManager;

  onDoubleClick(schematics, handler, event) {
    let params;

    /**
     * event.cdSerie = NR_SEQUENCIA from DIC_OBJETO (folder "HTML5 - Series"
     * inside the folder "WChartSeries" on the Feature "Object Dictionary").
     * Usualy each serie is a specific chart color/area.
    */

    switch (event.cdSerie) {
      case 22222: // Area with color "GREEN" on the chart.
        params = {
          type: 555, // nrSeqText = Good
        }
        break;
      case 33333: // Area with color "YELLOW" on the chart.
        params = {
          type: 666, // nrSeqText = Intermediate
        }
        break;
      case 44444: // Area with color "RED" on the chart.
        params = {
          type: 777, // nrSeqText = Bad
        }
        break;
    }

    /**
     * event.record = Is one sql registered that return from sql executation
     * from DIC_OBJETO.DS_SQL (folder "WChartSeries" on the Feature "Object
     * Dictionary").
    */

    if (params && event.record) {
      params.expirationDateIni = event.record.DT_INI;
      params.expirationDateFin = event.record.DT_FIN;

      this.externalAccessManager.doOpenExternal(403, 'externalAccess', params);
    } else {
      const conf = {
        type: this.DialogBoxType.ABORT,
        message: 88888, // The Details function could not be opened.
        defaultShow: 'OK'
      };

      this.tasyWdialogbox(conf);
    }
  }
}
```
    This will bring up the select's record and serie code corresponding to the chart area that the user are double-clicking. Below are charts that send each of the mentioned values:
      * Stacked-bar: cdSerie and record;
      * Pie (português: Grágico de Pizza): record;
      * Doughnut: record;
      * Gauge: cdSerie and record;

### onAfterActivate
This event is fired after the component if fully activated, with it's series loaded. It passes nothing as parameters.

### onFilterReady
This event is fired after the filter of the component is ready. It passes nothing as parameter.

### onBeforeFilter
This event is fired before the action of filtering the component data happen. It passes nothing as parameter.

### onLegendItemClick
This event is fired when a legend item is clicked. It passes along the following parameters:

```
legendKey, serieValues
```

### onTouchStart

    This event is fired before the user touch on the TableChart's bar.

#### :bookmark_tabs: Parameters
**schematic:** _(Object)_  Schematic handler object <br>
**handler:** _(Object)_ Component handler <br>
**event:** _(Object)_ Event is a object build to framework. And each chart have specific values. <br>

#### :pencil2: Example
```javascript
import { Controller, Inject } from '@philips/odin-ext';
import { WChart } from 'odin-utils/controllers';

@Controller({ domain: 'nicu/NicuST', code: 11111 })
export default class StackedBarChartTest extends WChart {

  @Inject('DialogBoxType')
  DialogBoxType;

  @Inject('tasyWdialogbox')
  tasyWdialogbox;

  onTouchStart(schematics, handler, event) {
    const conf = {
      type: this.DialogBoxType.CONFIRM,
      message: 22222, // Would you like to render the chart?
      defaultShow: 'BOTH',
      cancelButtonCode: 33333, // Cancel
      okButtonCode: 44444 // Ok
    };

    this.tasyWdialogbox(conf).then(() => {
      handler.renderChart();
    });
  }
}
```


## Handler Methods
- [showDonutCenterText](#showDonutCenterText)
- [hideDonutCenterText](#hideDonutCenterText)
- [renderChart](#renderChart)
- [<s>setYAxisMaxValue</s>](#setYAxisMaxValue)
- [getYAxisMaxValue](#getYAxisMaxValue)
- [<s>setYAxisMinValue</s>](#setYAxisMinValue)
- [getYAxisMinValue](#getYAxisMinValue)
- [activate](#activate)
- [addSerie](#addserie)
- [addSerieToDontShowMarker](#addserietodontshowmarker)
- [removeSerieFromDontShowMarker](#removeseriefromdontshowmarker)
- [setSortFunction](#setsortfunction)
- [addValueToSerie](#addvaluetoserie)
- [changeSerieVisibility](#changeserievisibility)
- [changeSerieType](#changeserietype)
- [changeTypeChart](#changetypechart)
- [getSeries](#getseries)
- [removeSerieByName](#removeseriebyname)
- [toggleZoom](#togglezoom)
- [activeLoading](#activeloading)
- [setZoomRange](#setzoomrange)
- [setAxisY2Values](#setaxisy2values)
- [setCode](#setcode)
- [saveChartAs](#savechartas)
- [setTooltipStyle](#settooltipstyle)
- [setVisibleHeader](#setvisibleheader)
- [setClamp](#setclamp)
- [setPadding](#setpadding)
- [setDataLabelDisplayFunc](#setDataLabelDisplayFunc)
- [setHandlebarVisible](#setHandlebarVisible)
- [setTooltipFunction](#setTooltipFunction)
- [setSliderVisibility](#setSliderVisibility)
- [isChartEmpty](#isChartEmpty)
- [setSliderVisibility](#setSliderVisibility)
- [isChartEmpty](#isChartEmpty)
- [setLegendsSliderVisible](#setLegendsSliderVisible)
- [useRoundScale](#useRoundScale)
- [saveChartAsPdf](#saveChartAsPdf)
- [mockActivate](#mockActivate)
- [setShowAverageLine](#setShowAverageLine)
- [getShowAverageLine](#getShowAverageLine)
- [addLabelLastValue](#addLabelLastValue)
- [setRangeAxes](#setRangeAxes)


### showDonutCenterText
----
#### :page_with_curl: Description
Use it to show the donut chart center text. By default the text is shown. <br>

#### :bookmark_tabs: Parameters
**forceUpdate:** _(boolean)_ if true, the chart will be immediately updated. When used inside [onLoad](#onLoad) event, for example, it will already be built using this configuration, so it isn't necessary to pass the forceUpdate flag. If you need to update the chart in another context, you can do it through the method [renderChart](#renderChart). <br>

#### :leftwards_arrow_with_hook: Return
Not available. <br>

#### :pencil2: Example
```javascript
import { Controller } from '@philips/odin-ext';
import PFCSChartBase from '../../../commons/controllersBase/PFCSChartBase';
import { PA_TOTAL_PATIENTS } from '../../../commons/EnumPfcsDetails';

@Controller({ domain: 'pfcs/PfcsPA', code: 1139481 })
export default class TotalPatientsWC extends PFCSChartBase {
  onLoad() {
    // You dont need to pass the flag forceUpdate here. Check docs.
    this.handler.hideDonutCenterText();
  }
}
```

### hideDonutCenterText
----
#### :page_with_curl: Description
Use it to hide the donut chart center text. <br>

#### :bookmark_tabs: Parameters
**forceUpdate:** _(boolean)_ if true, the chart will be immediately updated. When used inside [onLoad](#onLoad) event, for example, it will already be built using this configuration, so it isn't necessary to pass the forceUpdate flag. If you need to update the chart in another context, you can do it through the method [renderChart](#renderChart). <br>

#### :leftwards_arrow_with_hook: Return
Not available. <br>

#### :pencil2: Example
```javascript
import { Controller } from '@philips/odin-ext';
import PFCSChartBase from '../../../commons/controllersBase/PFCSChartBase';
import { PA_TOTAL_PATIENTS } from '../../../commons/EnumPfcsDetails';

@Controller({ domain: 'pfcs/PfcsPA', code: 1139481 })
export default class TotalPatientsWC extends PFCSChartBase {
  onLoad() {
    if (this.canShowDonutText) {
      this.handler.showDonutCenterText();
    } else {
      this.handler.hideDonutCenterText();
    }
  }
}
```

### reactivate
----
#### :page_with_curl: Description
Reinitializes the chart with new data according to its type. <br>

#### :bookmark_tabs: Parameters
``aditionalSeries``: An ObjectArray. Is used to pass the activation params of the chart. Must contain at the least two keys, SEQUENCIA & PARAMBYNAME. <br>
``chartType``: _(string)_ Is passed for initializing a specific type of chart. <br>

#### :leftwards_arrow_with_hook: Return
Not available. <br>

#### :pencil2: Example
```javascript
component.attr('w-onload', 'onloadcustomchart($handler)');

scope.onloadcustomchart = $handler => {
  schematics.setFunctionVariables(!isTab ? div : tab, $handler);
  $handler.setParams('param', this.eisAteDrService.getdsSqlGraph());
  $handler.reactivate(activationParams,WChartConstants.AREA);
};
```

### renderChart
----
#### :page_with_curl: Description
Makes the chart to render accordingly to its type. <br>

#### :bookmark_tabs: Parameters
**isResizeListener:** _(boolean)_ only used for donut charts. If false, it will recreate the chart using the current set configurations. <br>

#### :leftwards_arrow_with_hook: Return
Not available. <br>

#### :pencil2: Example
```javascript
import { Controller } from '@philips/odin-ext';
import PFCSChartBase from '../../../commons/controllersBase/PFCSChartBase';
import { PA_TOTAL_PATIENTS } from '../../../commons/EnumPfcsDetails';

@Controller({ domain: 'pfcs/PfcsPA', code: 1139481 })
export default class TotalPatientsWC extends PFCSChartBase {
  onLoad() {
    if (this.aReasonToForceChartReRender) {
      this.handler.renderChart();
    }
  }
}
```

### <s>setYAxisMaxValue</s>
----
#### :page_with_curl: Description
(This method is currently unused)<br>
You can use this method - [setRangeAxes](#setRangeAxes)<br>

#### :pencil2: Example
```javascript
import { Controller } from '@philips/odin-ext';
import WChart  from '../../../commons/controllersBase/PFCSChartBase';

@Controller({ domain: 'atepac/AtePacAA', code: 123456 })
export default class MyChart extends WChart  {
  onLoad() {
    this.handler.setYAxisMaxValue(100);
  }
}
```

### getYAxisMaxValue
----
#### :page_with_curl: Description
Get the maximum value of the y axis in a chart<br>

#### :bookmark_tabs: Parameters
Not available. <br>

#### :leftwards_arrow_with_hook: Return
**yAxisMaxValue:** _(number)_ the maximum value setted in the y axis in the chart <br>

#### :pencil2: Example
```javascript
@Controller({ domain: 'atepac/AtePacAA', code: 123456 })
export default class MyChart extends WChart  {
  onLoad() {
    const maxValue = this.handler.getYAxisMaxValue();
  }
}
```

### <s>setYAxisMinValue</s>
----
#### :page_with_curl: Description
(This method is currently unused)<br>
You can use this method - [setRangeAxes](#setRangeAxes)<br>

#### :pencil2: Example
```javascript
@Controller({ domain: 'atepac/AtePacAA', code: 123456 })
export default class MyChart extends WChart  {
  onLoad() {
    this.handler.setYAxisMinValue(0);
  }
}
```

### getYAxisMinValue
----
#### :page_with_curl: Description
Get the minimum value of the y axis in a chart<br>

#### :bookmark_tabs: Parameters
Not available. <br>

#### :leftwards_arrow_with_hook: Return
**yAxisMaxValue:** _(number)_ the minimum value setted in the y axis in the chart <br>

#### :pencil2: Example
```javascript
@Controller({ domain: 'atepac/AtePacAA', code: 123456 })
export default class MyChart extends WChart  {
  onLoad() {
    const minValue = this.handler.getYAxisMinValue(0);
  }
}
```

### activate
This method is used to activate the chart. It can be used along with three parameters:

#### Parameters
``params`` An ObjectArray. Is used to pass the activation params of the chart. Must contain at the least two keys, ``SEQUENCIA`` & ```PARAMBYNAME```. This parameter is mandatory.
``chartSize`` An Object. Is used to pass the chart size, must contain two keys, ``height`` and ``width``. For the values can be the fixed size using the unit pixels (px) or a variable size using percent (%)
``filterValues`` An Object. Is used to pass filter values when activating the chart.

#### Example
```javascript
let chartParams = [
  {
    SEQUENCIA: 329457,
    PARAMBYNAME: {
      ie_agrupamento: '2',
      dt_mesano_inicial: '01/05/2014',
      dt_mesano_final: '01/05/2016',
      cd_estabelecimento: 1,
      cd_estrutura: '40'
    }
  }
];

let chartSize = {
  width: '400px';
  height: '400px';
}

chartHandler.activate(chartParams, chartSize);
```


### addSerie
This method adds a new series to the chart.

#### Parameters
``id`` A String. This will be the ID of the series.
``serieLabel`` A String. This will be used as a label for the series legend.
``color`` A String. The color of the series. If it is null or undefined, it will use a generated color from the color palette.
``yColumn`` An Array. This will be used as the column Y values.
``xValues`` An Array. This will be used as the X's values for the Y column. If null or undefined, the default X column will be used.
``chartType`` A String. This parameter can be null or undefined. If it's not specified, it will use the same chart type that is saved on the wChart dictionary object.
``dateFormat`` The mask used to format dates.
``sort`` If the component should sort the axis X column.
``sortAscending`` Used together with the sort parameter, it specify the order of the sort. If true, will sort the axis in an ascending way, of false, in a descent way.

#### Example
```javascript
const Y = [50, 100, 200];
const X = ['Cars', 'Motorcycles', 'Trains'];

handler.addSerie('TRANSPORTATIONS', 'People vs. Transportations', '#C3C3C3', Y, X);

const Y2 = [10, 20, 30];

handler.addSerie('ANIMALS', 'Animals who ride transports', undefined, Y2);

const Y3 = [50, 500, 700];

handler.addSerie('BBIRDS', 'Birds', undefined, Y3, undefined, 'line');
```


### addSerieToDontShowMarker
This method is used to add a serie to don't show the marker.

#### Parameter
``id`` A String. This will be the ID of the series.

#### Example
```javascript
handler.addSerieToDontShowMarker('serieId');
```

### removeSerieFromDontShowMarker
This method is used to remove a serie from the don't show the marker

#### Parameter
``id`` A String. This will be the ID of the series.

#### Example
```javascript
handler.removeSerieFromDontShowMarker('serieId');
```


### setSortFunction
Method used to specify a sort function to the axis x column. [See also the documentation to the native sort method](https://developer.mozilla.org/en/docs/Web/JavaScript/Reference/Global_Objects/Array/sort)

#### Parameters
``fn`` The sort function.

#### Example
```javascript
const sortfn = (a, b) => {
  if (something) return 1;
  if (somethingElse) return -1;

  return 0;
};

handler.setSortFunction(sortfn);
```


### addValueToSerie

#### Parameters
This method is used to add a new value to a existent series.
``id`` A String. This will be the ID of the series.
``x`` A String or a Number. This will be the X value where the value will be inserted.
``y`` A String or a Number. This will be the Y value where the value will be inserted.
``xPosition`` A Number. If the X value informed doesn't exist, and this is parameter isn't null or undefined, it will put the X value at the given position. If this is null or undefined, the X value will be inserted at the begin of the chart X column.

#### Example
```javascript
handler.addValueToSerie('TRANSPORTATIONS', 'Airplanes', 700, 3);
```


### changeSerieVisibility
This method is used to hide or show a series within a chart.

#### Parameters
``id`` A String. This will be the ID of the series.
``visibility`` A boolean. This will be used to determinate if the series will be showed or hidden.

#### Example
```javascript
handler.changeSerieVisibility('TRANSPORTATIONS', false);
```

### changeSerieType

#### Parameters
This method is used to change the type of a specific series. It is important to keep in mind that the new type must be compatible with current chart X's and Y's columns.
``id`` A String. This will be the ID of the series.
``type`` A String. The new type of the series, it can be one of the following: line, spline, step, area, area-spline, area-step, bar, scatter, pie, donut, gauge.

#### Example
```javascript
handler.changeSerieType('TRANSPORTATIONS', 'spline');
```


### changeTypeChart
This method is used to change the type of all series in a chart.

#### Parameters
``type`` A String. The new type of the series, it can be one of the following: line, spline, step, area, area-spline, area-step, bar, scatter, pie, donut, gauge.

#### Example
```javascript
handler.changeTypeChart('area-spline');
```


### getSeries
This method is used to get the series list currently in th chart, with the X and Y values.

Each of the series returned will contains the following properties: ``id`` and ``values``.

``id`` A String that state the ID of the series.
``values`` An Array of objects. Each object contains the value for a position on the series.

#### Example
```javascript
const series = handler.getSeries();

series.forEach(serie => {
  //do something
});
```


### removeSerieByName
This method is used to remove series by it's name.

#### Parameters
``serieName`` This parameter can be a single string or an array of strings.

#### Example
```javascript
handler.removeSerieByName('TRANSPORTATIONS');

//or

handler.removeSerieByName('TRANSPORTATIONS', 'INDUSTRIES');
```


### toggleZoom
This method is used to enable or disable the zoom feature of charts.

#### Example
```javascript
handler.toggleZoom();
```


#### keepLoading
When its necessary a request to the banckend to bring new data for chart, use keepLoading.

#### Example
```javascript
function onAfterActivate(schematics, chart, event) {
  chart.setKeepLoading(true);

  executeRequest().then((data) =>{
    // set values chart
    chart.setKeepLoading(false);
  });
}
```

### setZoomRange
This method is used to zoom in/out the chart.

#### Parameters
``start`` This parameter indicates the start of the zoom range.
``end`` This parameter indicates the end of the zoom range.

#### Example
```javascript
handler.setZoomRange(0, 10);
```


### setAxisY2Values
This method is used to set the values used by the y2 axis. If this method is used, the default c3 generated values for the axis will be overridden by the ones passed as parameters.

#### Parameters
``values`` An array indicating the values to be used as axis y2.
``concat`` A boolean that defaults to false, indicating if the values passed should be concatenated by previously passed ones.

#### Example
```javascript
handler.setAxisY2Values([10, 20, 30, 40, 50]);

handler.setAxisY2Values([60, 70, 80, 90, 100], true);
```


### setCode
This method is used to change the component code that will be used in it's activation process.

#### Parameters
``code`` A integer that indicates the new component code.
``reactivate`` A boolean indicating if the component should reactivate after setting the new code. Defaults to false.

#### Example
```javascript
handler.setCode(9999);
//something else
handler.reactivate();

//or

handler.setCode(99999, true);
```


### saveChartAs
This method is used to download and save the full chart image to the user computer.

#### Parameters
``filename`` A string that indicates the filename, if undefined, the chart id will be used instead.

#### Example
```javascript
const filename = 'my-cool-chart';
handler.saveChartAs(filename);
```

### saveChartAsPdf
----
#### :page_with_curl: Description
This method is used to download and save the full chart pdf to the user computer.
#### :bookmark_tabs: Parameters
**filename:** _(String)_ The filename, if undefined, the chart id will be used instead.
#### :leftwards_arrow_with_hook: Return
Not available.
#### :pencil2: Example
```javascript
@Controller({ domain: 'corEis/EisAteDR', code: 1196314, parent: 1173208 })
export default class ExportarPDFWJMI extends WPUMC {

  async action(schematics) {
    const code = `.tab-${schematics.getFunctionVariables().TAB_SEQ}`;
    const wchart = schematics.getFunctionVariable(`${code}`);
    wchart.saveChartAsPdf(`file-${schematics.getFunctionVariables().TAB_SEQ}`);
  }
}
```

### mockActivate
This method is used to activate the chart by mocking the data of the chart which will be received by the server. It can be used along with one mandatory parameter and three optional parameters:

#### Parameters
`mockData` An object similar to that which is being received from the server a similar object is required to pass it so the function can depict the same functionality as the activate method.
``params`` An ObjectArray. Is used to pass the activation params of the chart. Must contain at the least two keys, ``SEQUENCIA`` & ```PARAMBYNAME```. This parameter is mandatory.
``chartSize`` An Object. Is used to pass the chart size, must contain two keys, ``height`` and ``width``. For the values can be the fixed size using the unit pixels (px) or a variable size using percent (%)
``filterValues`` An Object. Is used to pass filter values when activating the chart.

#### Example
```javascript
const mockData = {
  series: [
      {
          cdChart: 123456,
          uniqueKey: 123456,
          incProportional: false,
          fieldLegend: "Issues",
          fieldX: "Day",
          fieldY: "Issues",
          masksX: "date(timestamp)",
          dsSerie: "Sprint 1 issues",
          serieType: "bar",
          typeColumnX: "DATE",
          typeColumnY: "NUMBER",
          rotate: false,
          values: [
              {
                  valueAxisX: new Date("2022-03-14T10:00:00Z"),
                  valueAxisY: "5",
                  dsLegend: "2022-03-14 10:00:00",
                  markVisible: true,
                  prediction: false
              },
              {
                  valueAxisX: new Date("2022-03-15T10:00:00Z"),
                  valueAxisY: "22",
                  dsLegend: "2022-03-15 10:00:00",
                  markVisible: true,
                  prediction: false
              },
              {
                  valueAxisX: new Date("2022-03-16T10:00:00Z"),
                  valueAxisY: "10",
                  dsLegend: "2022-03-16 10:00:00",
                  markVisible: true,
                  prediction: false
              }
          ],
          properties: {},
          ieCantShowMarker: "N",
          gantt: false,
          showGnattMarks: false,
          nrSeqApres: 1,
          visible: true
      },
      {
        cdChart: 123457,
        uniqueKey: 123457,
        incProportional: false,
        fieldLegend: "Issues",
        fieldX: "Day",
        fieldY: "Issues",
        masksX: "date(timestamp)",
        dsSerie: "Sprint 2 issues",
        serieType: "bar",
        typeColumnX: "DATE",
        typeColumnY: "NUMBER",
        rotate: false,
        values: [
            {
                valueAxisX: new Date("2022-03-14T10:00:00Z"),
                valueAxisY: "8",
                dsLegend: "2022-03-14 10:00:00",
                markVisible: true,
                prediction: false
            },
            {
                valueAxisX: new Date("2022-03-15T10:00:00Z"),
                valueAxisY: "11",
                dsLegend: "2022-03-15 10:00:00",
                markVisible: true,
                prediction: false
            },
            {
                valueAxisX: new Date("2022-03-16T10:00:00Z"),
                valueAxisY: "15",
                dsLegend: "2022-03-16 10:00:00",
                markVisible: true,
                prediction: false
            }
        ],
        properties: {},
        ieCantShowMarker: "N",
        gantt: false,
        showGnattMarks: false,
        nrSeqApres: 1,
        visible: true
    }
  ],
  chartProperties: {
      stringType: "B",
      type: "bar",
      title: "Daily issues resolved",
      propertiesNew: {}
  }
};

chartHandler.mockActivate(mockData);
```

### setTooltipStyle
This method sets the style in which the value should be displayed in the tooltip.

#### Parameters
``style`` One of the items of the `wChartTooltipConstants` constant.

#### Possible styles:
Consider two charts with the following series:

 - `Chart A: { ySeries: [1, 3] }`
 - `Chart B: { ySeries: [1, 3] and [3, 2] }`

The table bellow shows how the tooltip format will be for each of the charts, when hovering over the second item of the first serie.

|Style|Chart A (value: 3)|Chart B (values: 3, 2)|
|:---|---|---|
|`wChartTooltipConstants.REAL`|3|3|
|`wChartTooltipConstants.PERCENT`|100%|100%|
|`wChartTooltipConstants.PERCENT_OF_ALL_SERIES`|100%|60%|
|`wChartTooltipConstants.PERCENT_REAL`|100% of 3|100% of 3|
|`wChartTooltipConstants.PERCENT_REAL_OF_ALL_SERIES`|100% of 3|60% of 5|

#### Example
```javascript
handler.setTooltipStyle(wChartTooltipConstants.REAL);
```


  * #### rotateAxes
  Do the reversal of the axes, has no parameters.

  ##### Example:
  ```javascript
  handler.rotateAxes();
  ```


  * #### setAxesVisibility
  Configure chart legend visibility.

  | Params | Type | Description |
  | --- | --- | --- |
  | axis | String | The axis: **x** or **y** |
  | show | Boolean | Show or hide axis |

  ##### Example:
  ```javascript
    handler.setAxesVisibility("x", true); //default TRUE
    handler.setAxesVisibility("y", false); //default TRUE
  ```


  * #### setAxesTitle
  Configure chart legend visibility.

  | Params | Type | Description |
  | --- | --- | --- |
  | labels | ObjectArray | The array of objects with keys **x** or **y** |

  ##### Example:
  ```javascript
    let labels = {y: 'Y Label', x: 'X Label'};
    handler.setAxesTitle(labels);
  ```


  * #### setGridXVisible
  Add optional grid lines on x grid.

  | Params | Type | Description |
  | --- | --- | --- |
  | show | Boolean | The visibility of grid x  |

  ##### Example:
  ```javascript
    $scope.handler.setGridXVisible(true); //default false
  ```


  * #### setGridYVisible
  Add optional grid lines on y grid.

  | Params | Type | Description |
  | --- | --- | --- |
  | show | Boolean | The visibility of grid y  |

  ##### Example:
  ```javascript
    $scope.handler.setGridYVisible(true); //default false
  ```


  * #### setHorizontalMarkLine
  Add optional mark line on y value.

  | Params | Type | Description |
  | --- | --- | --- |
  | value | Number | the y value to show the mark  |
  | labelText | String | the label text of mark  |

  ##### Example:
  ```javascript
    handler.setHorizontalMarkLine(400, "Label");
  ```


  * #### setEmptyValuesTooltipVisibility
  Configure empty values visibility on chart tooltip.

  | Params | Type | Description |
  | --- | --- | --- |
  | show | Boolean | Show or hide empty values in tooltip |

  ##### Example:
  ```javascript
   handler.setEmptyValuesTooltipVisibility(false); //default TRUE
  ```

  * #### setLegendVisibility
  Configure chart legend visibility.

  | Params | Type | Description |
  | --- | --- | --- |
  | show | Boolean | Show or hide chart legend |

  ##### Example:
  ```javascript
   handler.setLegendVisibility(false); //default TRUE
  ```


  * #### setRangeAxes
  Set axes range.

  | Params | Type | Description |
  | --- | --- | --- |
  | axis | String | The axis: **x** or **y** |
  | initialRange | **String/Number** | The initial range of chart |
  | finalRange | **String/Number** | The final range of chart |

  ##### Example:
  ```javascript
   //if the range is a Number , it will be understood as the axis index
   handler.setRangeAxes('x', 1, 2);
   handler.setRangeAxes('y', 200, 500);

   //if the range is a String will be understood as the axis Label
   handler.setRangeAxes('x', '10-05-2015 00:00:00', '10-05-2015 23:59:59');
  ```


  * #### setSeriesColor
  Define the series color.

  | Params | Type | Description |
  | --- | --- | --- |
  | colorConfig | ObjectArray | The array with **series** and **colors** objects |

  ##### Example:
  ```javascript

  let colorConfig = [];
  let serieColor = {};

  serieColor['serie'] = 'VL_CONSUMO';
  serieColor['color'] = '#eee';

  colorConfig.push(serieColor);

  handler.setSeriesColor(colorConfig); //change one serie color

  //or directly

  handler.setSeriesColor( [{serie: 'VL_CONSUMO', color: '#ff0000'},
                          {serie: 'VL_ESTOQUE', color: '#00ff00'},
                          {serie: 'VL_COMPRA',  color: '#0000ff'}] ) //change tree series color
  ```


  * #### setSerieColor
  Define one serie color.

  | Params | Type | Description |
  | --- | --- | --- |
  | serie | String | The string with the serie identificator |
  | color | String | The string with the new color |

  ##### Example:
  ```javascript

  handler.setSerieColor('VL_CONSUMO', '#ff0000');
  ```


  * #### setSeriesLabel
  Define the series label.

  | Params | Type | Description |
  | --- | --- | --- |
  | labelConfig | ObjectArray | The array with **series** and **labels** objects |

  ##### Example:
  ```javascript

  let labelConfig = [];
  let serieLabel = {};

  serieLabel['serie'] = 'VL_CONSUMO';
  serieLabel['label'] = 'NEW LABEL SERIE';

  labelConfig.push(serieLabel);

  handler.setSeriesLabel(labelConfig);

  //or directly

  handler.setSeriesLabel( [{serie: 'VL_CONSUMO', label: 'CONSUMO LABEL'},
                          {serie: 'VL_ESTOQUE', label: 'ESTOQUE LABEL'},
                          {serie: 'VL_COMPRA',  label: 'COMPRA LABEL'}]);
  ```


  * #### setSerieLabel
  Define one serie label.

  | Params | Type | Description |
  | --- | --- | --- |
  | serie | String | The string with the serie identificator |
  | label | String | The string with the new label |

  ##### Example:
  ```javascript
  handler.setSerieLabel('VL_CONSUMO', 'NEW LABEL');
  ```

  * #### setTitleChart
  Puts a title on the chart.

  | Params | Type | Description |
  | --- | --- | --- |
  | title | String | The charts title |
  | serieKeyName | String | The serie key name |

  ##### Example:
  ```javascript
  handler.setTitleChart('Chart Title');
  ```


  * #### setVerticalMarkLine
  Add optional mark line on x value.

  | Params | Type | Description |
  | --- | --- | --- |
  | value | String/Number | the x value to show the mark  |
  | labelText | String | the label text of mark  |

  ##### Example:
  ```javascript
  //if the value is a Number , it will be understood as the axis index
  handler.setVerticalMarkLine(6, "Label");

   //if the range is a String will be understood as the axis Label
  handler.setVerticalMarkLine('2014-06-01', "Label");
  ```


  * #### setZoom
  Configure Zoom of the chart.

  | Params | Type | Description |
  | --- | --- | --- |
  | zoom | Boolean | Enable or disable zoom property of the serie |

  ##### Example:
  ```javascript
   handler.setZoom(true); //default FALSE
  ```


  * #### toggleZoom
  Enable\Disable Zoom to the chart.
  Has no parameters, if zoom is enabled will set disabled, and vice versa.

  ##### Example:
  ```javascript
   handler.toggleZoom()
  ```


  * #### getElement
  Get the wChart HTML element.

  ##### Example:
  ```javascript
    let wchart = handler.getElement();

    doSomething(wchart);
  ```


  * #### exportChart
  Exports the full chart to a file, returning a promise that resolves to the file name.

  ##### Example:
  ```javascript
    handler.exportChart()
      .then(filename => {
        doSomething(filename);
      })
      .catch(err => {
        handleErrors(err);
      });
  ```

----
### setVisibleHeader

#### :page_with_curl: Description
Change the visibility of the header.

The header contains: Title and HandleBar.

#### :bookmark_tabs: Parameters
**visible:** _(Boolean)_  _true_ to display the header, _false_ to hide the header

#### :leftwards_arrow_with_hook: Return
Not available

#### :pencil2: Example
```javascript
@Controller({ domain: 'atePac/atePac', code: 123456 })
export default class Chart extends Chart {

  onLoad(schematics, chart) {
    chart.setVisibleHeader(false);
  }
}
```

----
### setHandlebarVisible

#### :page_with_curl: Description
Change the visibility of the handlebar.

#### :bookmark_tabs: Parameters
**visible:** _(Boolean)_  _true_ to display the handlebar, _false_ to hide the handlebar

#### :leftwards_arrow_with_hook: Return
Not available

#### :pencil2: Example
```javascript
@Controller({ domain: 'atePac/atePac', code: 123456 })
export default class Chart extends Chart {

  onLoad(schematics, chart) {
    chart.setHandlebarVisible(false);
  }
}
```

----
### setTooltipFunction

#### :page_with_curl: Description
Changes the histogram tooltip according to a function returning its template. The following parameters can be customized: <br>

- **Font-size**: this parameter can be defined using one of two css classes: **"text-small"** and **"text-big"**.

- **Text alignment**: this parameter can be defined using one of two css classes: **"align-left"** (aligns text to the **left** side)
 and **"align-right"** (aligns text to the **right** side).

- **Template structure**: the developer can set an HTML template structure customized according to its preferences on the tooltip.

Please note that the function created by the developer containing the desired template may have up to 2 parameters:

- **y**: this parameter is used to display the histogram Y-Axis value. Using it will show the hovered point y-value in the histogram.

- **x**: this parameter is an object used to display the hovered point date (**x.dateValue**) and day (**x.stringDate**). Using it will show the hovered point date in the histogram and/or if the date is equal to today, tomorrow or yesterday. Please note that
x.dateValue can be formatted according to the developer preferences. The syntax is **"x.dateValue.format(*formatPattern*)"**.
A full list of date formatting patterns can be found at [Moment.js official documentation](https://momentjscom.readthedocs.io/en/latest/moment/04-displaying/01-format/).



#### :bookmark_tabs: Parameters
**function:** _(function)_  A function returning the template customized according to the developer structure. This function must have **(x, y)** parameters. Default template is: <br>
```javascript
<div class="text-small align-left">${x.format('hh:mm A')}</div>
<div class="text-small align-left">${x.format('DD MMM YYYY')}</div>
<div class="text-big align-left">${data.y}</div>
```

#### :leftwards_arrow_with_hook: Return
Not available

#### :pencil2: Example
```javascript
@Controller({ domain: 'atePac/atePac', code: 123456 })

// Developer template function
  function getTooltipParameters (x, y) {

    const currentDate = moment(); //Get current date using Moment.js
    let stringDate = 'Today';

        //An example logic to display "Today", "Tomorrow" or "Yesterday" on tooltip
        if (x !== currentDate.startOf('day')) {
          var tomorrow = currentDate.clone().add(1, 'day').startOf('day');
          var yesterday = currentDate.clone().subtract(1, 'day').startOf('day');

          if (x.isSame(tomorrow, 'day')) {
            stringDate = 'Tomorrow';
          }

          if (x.isSame(yesterday, 'day')) {
            stringDate = 'Yesterday';
          }
        }

    // The first div will display "Today", "Tomorrow" or "Yesterday" depending on the x-axis value. It will have a big font-size and will be set on the left side of the tooltip.

    // The second div will display the x-axis date in "dd-mmm-yyyy" format. It will have a small font-size and will be set on the right side of the tooltip.

    // The third div will display the y-axis value followed by a % symbol. It will have a big font-size and will be set on the left side of the tooltip.

      return `<div class="text-big align-left">${stringDate}</div>
              <div class="text-small align-right">${x.format('DD MMM YYYY')}</div>
              <div class="text-big align-left">${y}%</div>`;
    }

export default class Chart extends Chart {

  onLoad(schematics, chart) {
    chart.setTooltipFunction(getTooltipParameters); // The handler method receives the function above as an argument.
  }

}
```

### isChartEmpty
----
#### :page_with_curl: Description
Checks if the chart has no content.
#### NOTE: You will get richedit handler once the richedit is loaded.

#### :bookmark_tabs: Parameters
Not available.

#### :leftwards_arrow_with_hook: Return
**Boolean:** true if it is empty, false otherwise.

#### :pencil2: Example
```javascript
@Controller({ domain: 'atePac/atePac', code: 123456 })
export default class Chart extends Chart {
  onLoad(schematics, handler) {
    if (handler.isChartEmpty()) {
      // Do something
    } else {
      // Do something else
    }
  }
}
```
----

### setClamp

#### :page_with_curl: Description
When datalabels is activated, some times the label can overpass the limit of container. With clamp as true, the datalabel will calculate the positions and organize chart according new alues.

#### :bookmark_tabs: Parameters
**clamp:** _(Boolean)_ _true_ to adjust automatically the paddings of chart to show completely labels and _false_ to ignore limits of chart container.

#### :leftwards_arrow_with_hook: Return
Not available

#### :pencil2: Example
```javascript
import { WChart } from 'odin-utils/controllers';

@Controller({ domain: 'atePac/atePac', code: 123456 })
export default class ChartExample extends WChart {

  onLoad(schematics, chart) {
    chart.setClamp(false);
  }
}
```

----
### setPadding

#### :page_with_curl: Description
Set padding in the chart. You can specify individual values or just one value that will be applied for all sides.

#### :bookmark_tabs: Parameters
**verticalPadding:** _(Number)_ Set vertical spacing, in other words, a spacing will be included in top and bottom of chart container.<br>
**horizontalPadding:** _(Number)_ Set horizontal spacing, in other words, a spacing will be included in left and right of chart container.

#### :leftwards_arrow_with_hook: Return
Not available

#### :pencil2: Example
```javascript
import { WChart } from 'odin-utils/controllers';

@Controller({ domain: 'atePac/atePac', code: 123456 })
export default class ChartExample extends WChart {

  onLoad(schematics, chart) {
    chart.setPadding(5, 10);
  }
}
```


# Printing charts
Within the wChart component, there's the possibility to print the rendered chart.

## Reports
The process to print a chart can be achieved through the [WReportService](src/app/common/services/w-report#wreportservice), with this service, you can then print a report of the chart, or visualize it.

To get the chart image to use within the report service, one must use the [exportChart](#exportchart) method, and then include the returned file path in the report.

## Download
There's also the possibility to download the chart image using the [saveChartAs]() method.

# Gauge Chart
To make use of a gauge chart, it's necessary to create a WChart object with the 'gauge' type in the object dictionary.

## Sessions
In the gauge chart there's the possibility to have different sessions of data, all of which will come together to form the final arc.

Each session should be registred as a individual series, and the value of the ``Y axis attribute`` wil be used as the max value of that arc session.

## Needle
The value that the gauge needle should point to also need to be registered as a serie, and then on the WChart object custom properties, the ``Y axis attribute`` of the needle serie should be set on the ``gaugePointer`` property.

## Reference Values
When using the gauge chart, it will also be showed some reference values on specific degrees of the chart. To specify how many values should be showed, there's the property ``gaugeDegreesQuantity`` that accepts an numeric value.

The default number os values showed is set to 5.

## Methods
There's also the possibility to use some of the following methods along with the gauge chart:

# Gantt Chart
To create a gantt chart, it is necessary to create and feed data to the component programmatically through the methods documented in the [Feeding data and creating the Gantt chart](#feeding-data-and-creating-the-gantt-chart) section, it is also extremely recommended to check the [Data model](#data-model) section to understand how to pass data into the chart.

## Data model

### Activity object
The main object of the Gantt, that represent the rows of the chart.

#### Keys
```name``` a string that represents the name of the activity.
```tasks``` an array of objects containing all tasks related to the activity.

##### Example
```javascript
const act = {
  name: 'name',
  tasks: []
};
```


### Task object

#### Keys
```name``` a string that represents the name of the task.
```content``` a string that represents the content of the task. This value will be show in the task bar.
```progress``` a number representing the percentage of completeness of the task.
```start``` a ```moment``` object. It represents the start of the task.
```end``` a ```moment``` object. It represents the end of the task.
```status``` a string that represents the current status of the task, can either values from the ```wChartGanttTaskConstant``` constant object.

##### Example
```javascript
const task = {
  name: 'task_name',
  content: 'the content of the task',
  progress: 50,
  start: moment(),
  end: instantObj,
  status: wChartGanttTaskConstant.COMPLETE
};
```

## Feeding data and creating the Gantt chart
To feed data into the chart, one must use the methods made available through the builder object, which can be obtained through the ```getGanttBuilder``` method, from the wChart handler.

The builder object has the following methods:

#### addActivity
This method is used to add activities to the Gantt chart.

##### Example
```javascript
ganttBuilder.addActivity('Test')

// OR

const tasks = [task_0, task_1, task_2];

ganttBuilder.addActivity('Test', tasks)
```

#### addActivities
This method is similar to the ```addActivity``` method, but instead of multiple parameter passing, it accepts an array of activities, following the activity data model

##### Example
```javascript
const development = {
  name: 'Development',
  tasks: [dev_0, dev_1, dev_2, dev_3]
};

const testing = {
  name: 'Testing',
  tasks: [test_0, test_1, test_2]
};

const validation = {
  name: 'Validation & Verification',
  tasks: [vv_0, vv_1]
}

ganttBuilder.addActivities([development, testing, validation]);
```


#### addTask
This method is used to add a new task to an already existing activity.

##### Example
```javascript
const task = {
  name: 'create componet wButton',
  content: 'develop and test the wButton component',
  progress: 20,
  start: startDate,
  end: endDate,
  critical: false
};

ganttBuilder.addTask('development', task);

// OR

ganttBuilder.addTask('development', 'create component wButton', 'develop and test the wButton component', startDate, endDate, 20, 'Parado', '#b2e2ff', false);
```

#### addTasks
Similtar to the ```addTask``` method, this one receives an array of tasks, and concats the new tasks to the existing ones on the specified activity.

##### Example
```javascript
const task_0 = {
  name: 'create_wButton',
  content: 'develop and test the wButton component',
  progress: 20,
  start: startDate_0,
  end: endDate_0
};

const task_1 = {
  name: 'create_wChart',
  content: 'develop and test the wChart component',
  progress: 0,
  start: startDate_1,
  end: endDate_1
};

ganttBuilder.addTasks('development', [task_0, task_1]);
```


#### setStartDate
This method is used to set the start date of the Gantt chart. It accepts either a ```Instant``` or a ```moment``` object.

#### Example
```javascript
ganttBuilder.setStartDate(startDate);
```


#### setEndDate
This method is used to set the end date of the Gantt chart. It accepts either a ```Instant``` or a ```moment``` object.

#### Example
```javascript
ganttBuilder.setEndDate(endDate);
```


#### setViewScale
Column scale using any of momentJS#add() supported unit. <br />
See: [Angular Gantt - View Scale](https://www.angular-gantt.com/configuration/attributes/#view-scale)

#### Example
```javascript
ganttBuilder.setViewScale(viewScale);
```


#### setColumnWidth
The width of each column in `px`. <br />
See: [Angular Gantt - Column Width](https://www.angular-gantt.com/configuration/attributes/#column-width)

#### Example
```javascript
ganttBuilder.setColumnWidth(columnWidth);
```


#### setTreeHeader
Column header for the labels. <br />
See: [Angular Gantt - Tree](https://www.angular-gantt.com/plugins/tree/)

#### Example
```javascript
ganttBuilder.setTreeHeader(treeHeader);
```


### setAction
This method is used to change action for default handlebar buttons (reports).

#### Example
```javascript
chartHandler.setAction('print', function(){});
```


### setActionState
Changes the state of a item on the chart handlebar

### Parameters
``button`` Name of the handlebar item to recieve the state change. [WChartHandlebarConstants](https://github.com/philips-emr/tasy-framework/blob/dev/src/app/components/atoms/w-chart/constants/w-chart-handlebar.constant.js) contains the names of the avaible items.
``state``  Strings `ENABLED`, `DISABLED` or `HIDDEN`.

### Example
```javascript
{
  123: { // an example code of chart
    onReady: function(schematics, chartHandler){
      chartHandler.setActionState(WChartHandlebarConstants.SAVE_IMAGE, 'DISABLED');
  }
}
```

### getBase64
This method return a promise with string base64.

#### Example
```javascript
  handler.getBase64().then(data=>{
    console.log(data);
  });
```


## Inserting the chart on the page
When done configurating the chart, you can create the gantt chat through the chart handler and it will load into the screen, using the following code as example:

#### Example
```javascript
chartHandler.createGantt();
```


# Prediction Chart
See more information at Wiki clicking [here](https://github.com/philips-emr/tasy-framework/wiki/How-to:-Prediction-Chart).


# Chart Builder
Use to create the chart component externally, without using the schematics.

### addSerie
Add a new series to the chart.
#### Parameters
**code:** _(number)_ Unique code for this serie.<br>
**type:** _(string)_ Chart type to be created. See all types [here](https://github.com/philips-emr/tasy-framework/blob/dev/packages/framework/src/app/components/atoms/w-chart/constants/w-chart.constant.js).<br>
**description:** _(string)_ Label representation for value. Default is y value.<br>
**values:** _(array)_ Values represented in the chart. Use the method _createValue_ to create value object.<br>
**rotate:** _(boolean)_ Indicates the rotation of the chart.<br>
**maskX:** _(string|undefined)_ Mask for x axis label.<br>
**maskY:** _(string|undefined)_ Mask for y axis label.<br>
**colors:** _(string|array[string]|undefined)_ Colors for serie or values.<br>

### createValue
Create the value object to use in the series array.
#### Parameters
**x:** _(any)_ Indicates the position of value on x axis.<br>
**y:** _(any)_ Indicates the position of value on y axis.<br>
**description:** _(string)_ Text representation for value. Default is y value.<br>
**isTarget:** _(boolean)_ set to true if the value is a target. Default is false<br>

### Example
```javascript
class Dashboard extends WDashboard {
  addFakeDataOnChartBuilder(chartBuilder) {
    const values = [
      chartBuilder.createValue(9, 1),
      chartBuilder.createValue(7, 1),
      chartBuilder.createValue(6, 3),
      chartBuilder.createValue(5, 4),
      chartBuilder.createValue(4, 4),
      chartBuilder.createValue(3, 8, 'Target', true),
    ];

    chartBuilder.addSerie(1, 'bar', 'Qty.', values, true, 'date(short)',',.2f');

    return chartBuilder;
  }

  onAfterLoadRecords(schematics, wdashboard) {
    wdashboard.getCardHandler(code).setContentByBuilder(
      this.addFakeDataOnChartBuilder(wdashboard.getChartBuilderInstance()), true
    ); // the second parameter define if the chart will appear after render
  }
}
```


# Action
To use a action within a chart, you'll first have to configure your chart with the value @restricao on the SQL field of the object dictionary.

Afterwards, you'll need to create a new java class, with the ```WChartAction(nr_chart)``` annotation. Inside the created class, you can use annotations for each of the series, with the ```WChartSerieResource(nr_serie)``` annotation, as can be seen on the example below:

*** Attention when creating the methods annotated by the WChartSerieResource annotation, as the names can be anything you want.

#### Example
```java
@WChartAction(685920) //wchart
public class WChartActionSample {

 @WChartSerieResource(685922) //first serie
 public SQLModifierBuilder getFirstSerie(Map parameters){
   return new SQLModifierBuilder(parameters)
         .addRestriction("and 1 = 1")
         .addRestriction("and 'ltpereira' = :nm_usuario ")
         .addParameter("NM_USUARIO", "ltpereira");
 }

 @WChartSerieResource(685923) //second serie
 public SQLModifierBuilder getSecondSerie(Map parameters){
   return new SQLModifierBuilder(parameters)
         .addRestriction("and 2 = 2")
         .addRestriction("and 'mfklauberg' = :nm_usuario ")
         .addParameter("NM_USUARIO", "mfklauberg");
 }
}
```


## postProcessingAction
It's possible to manipulate data before its getting returned from backend.

The post processing action annotated classes will be found and called right before returning from backend, that's the exact point if you need to manipulate your data.
You'ill need to create an action interceptor, as shown below:

#### Example:
```java
import br.com.philips.tasy.dto.shared.wchart.WChartProperties;
import br.com.philips.tasy.dto.shared.wchart.WChartSeriesDTO;
import br.com.philips.tasy.dto.shared.wchart.WChartSeriesValueDTO;
import br.com.wheb.action.wChart.AbstractWChartPostProcessingAction;
import br.com.wheb.annotations.NamedAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@NamedAction(name = "postProcessSeries", referedInterface = AbstractWChartPostProcessingAction.class)
public class TecTesF1WChartPostProcessingAction extends AbstractWChartPostProcessingAction {

    @Override
    public ArrayList<WChartSeriesDTO> build(ArrayList<WChartSeriesDTO> series, Integer dictionaryCode, Map<String, Object> params,
                                            ArrayList<HashMap> aditionalSeries, Map<String, Object> filterValues, WChartProperties chartProperties) {
        ArrayList<WChartSeriesValueDTO> values1stSerie = new ArrayList();
        ArrayList<WChartSeriesValueDTO> values2ndSerie = new ArrayList();
        ArrayList<WChartSeriesValueDTO> values3rdSerie = new ArrayList();
        ArrayList<WChartSeriesValueDTO> values4thSerie = new ArrayList();
        String[] dates = {
            "01/07/2018",
            "02/07/2018",
            "03/07/2018",
            "04/07/2018",
            "05/07/2018",
            "06/07/2018",
            "07/07/2018",
            "08/07/2018",
            "09/07/2018",
            "10/07/2018",
        };

        for (int i = 0; i < 10; i++) {
            values1stSerie.add(createValueDTO(dates[i],400 - 10 * i));
            values2ndSerie.add(createValueDTO(dates[i],50 * i));
            values3rdSerie.add(createValueDTO(dates[i],200 - 2 * i));
            if (i == 1) {
                values4thSerie.add(createValueDTO(dates[i], 0));
            } else {
                values4thSerie.add(createValueDTO(dates[i],100 * i - 75));
            }
        }

        for (WChartSeriesValueDTO value : values1stSerie) {
            series.get(0).getValues().add(value);
        }
        for (WChartSeriesValueDTO value : values2ndSerie) {
            series.get(1).getValues().add(value);
        }
        for (WChartSeriesValueDTO value : values3rdSerie) {
            series.get(2).getValues().add(value);
        }
        for (WChartSeriesValueDTO value : values4thSerie) {
            series.get(3).getValues().add(value);
        }

        return series;
    }

    private WChartSeriesValueDTO createValueDTO(Object axisX, Object axisY) {
        WChartSeriesValueDTO value = new WChartSeriesValueDTO();
        value.setPrediction(true);
        value.setValueAxisX(axisX);
        value.setValueAxisY(axisY);
        return value;
    }
}
```


# Filters
To use filters within charts, you have to need to configure your chart serie with @restricao on the SQL field of the object dictionary.

## Filters with custom action
There's also the possibility to use custom actions with filters within charts, and to make use of this feature, you have to create a class that extends from ```AbstractWChartWithFilterAction```, as can be seen in the example bellow:

#### Example
```java
@NamedAction(name = "TecTesF1WChartWithFilterAction", referedInterface = AbstractWChartWithFilterAction.class)
public class TecTesF1WChartWithFilterAction extends AbstractWChartWithFilterAction {

	public TecTesF1WChartWithFilterAction(RequisicaoVO request) {
		super(request);
	}

	@Override
	public void build(Map<String, Object> params, Map<String, Object> filterValues) {
		addFilterRestriction(" AND ROWNUM < 2 ");
	}

}
```

Where the ```name``` parameter on the ```@NamedAction``` annotation is the name that you used on the ```action``` field on the filter properties.


# Labels in pointer charts
To use labels in point chart,  you have to need to configure your chart as example below.

#### Example
```javascript
@Controller({ domain: 'atePac/atePac', code: 123456 })
export default class WChart extends WChart {

  onReady(schematics, chart) {
    $scope.handler.setLabelFormatter((record, idSerie, serieIndex, rowIndex) => {
      // the return info must be information you want to display to the user
      // verify if the information you need is in the record
      // if the info doesn't exist on record, you must change the chart SQL
      // adding the column info you need, then it will e added on record
      return record.x.toString(); // default is null
    });

    $scope.handler.showLabels(true); // default false
  }

}
```

### setDataLabelDisplayFunc
----
#### :page_with_curl: Description
Use this method if you want to change the way the chart data labels are shown. <br>

#### :bookmark_tabs: Parameters
**dataLabelDisplayFunc:**  _(function)_ A function that will be used to display the data label. It receives three parameters ready for usage:
  * _**label:**_ Contains the chart data used to identify what is the current referred data. For example: Positive/Negative.
  * _**value:**_ The current referred chart data value formatted according to its type. For example: R$ 1234,00 or $ 1234.00.
  * _**percentage:**_ The current referred chart data percentage already calculated without format. <br>

#### :leftwards_arrow_with_hook: Return
Not available. <br>

#### :pencil2: Example
```javascript
import { Controller } from '@philips/odin-ext';
import PFCSChartBase from '../../../commons/controllersBase/PFCSChartBase';

@Controller({ domain: 'pfcs/PfcsCO', code: 1145480 })
export default class TestsResultsWC extends PFCSChartBase {
  onLoad() {
    this.handler.setDataLabelDisplayFunc((label, value) => `${label}: ${value}`);
  }
}

```

### setSliderVisibility
----
#### :page_with_curl: Description
Configure chart slider visibility.

#### :bookmark_tabs: Parameters
| Params | Type | Description |
| --- | --- | --- |
| show | Boolean | Show or hide chart slider |

#### :leftwards_arrow_with_hook: Return
Not available. <br>

#### :pencil2: Example:
```javascript
  handler.setSliderVisibility(true); //default FALSE
```

### setLegendsSliderVisible
----
#### :page_with_curl: Description
Configure slider legend visibility.

#### :bookmark_tabs: Parameters
| Params | Type | Description |
| --- | --- | --- |
| visible | Boolean | Show or hide legend slider |

#### :leftwards_arrow_with_hook: Return
Not available. <br>

### addVerticalLines

#### :page_with_curl: Description
Use this method if you want to put vertical lines on chart. (Only works on line chart) <br>

#### :bookmark_tabs: Parameters
**linesGrid:** _(array)_ Values represented in the chart to create the vertical lines.<br>

#### Example
```javascript
const linesGrid = [
            { value: 100, text: 'Line 1' },
            { value: 200, text: 'Line 2' },
            { value: 300, text: 'Line 3' },
            { value: 400, text: 'Line 4' }
          ]

handler.addVerticalLines(linesGrid);
```

#### :pencil2: Example:
```javascript
import { Controller, Inject } from '@philips/odin-ext';
import { WChart } from 'odin-utils/controllers';

@Controller({ domain: 'nicu/NicuAn', code: 1111111 })
export default class FluidBalanceChartWChart extends WChart {

  onLoad(schematics, handler) {
    handler.setLegendsSliderVisible(true);
  }
}
```

### setShowAverageLine

#### :page_with_curl: Description
Set the value to show the average line inside the chart. (It's only works on Stacked Bar chart)<br>

#### :bookmark_tabs: Parameters
**showAverageLine:** _(boolean)_  _true_ to display the average line, _false_ to hide the average line.<br>

#### Example
```javascript
handler.setShowAverageLine(showAverageLine);
```

### getShowAverageLine

#### :page_with_curl: Description
Get the value to show the average line inside the chart. (It's only works on Stacked Bar chart) <br>

#### :bookmark_tabs: Parameters
Not available. <br>

#### Example
```javascript
handler.getShowAverageLine();
```

### addLabelLastValue

#### :page_with_curl: Description
Set the last values in the lines to show on colum. (It's only works on Line chart) <br>

#### :bookmark_tabs: Parameters
Not available. <br>

#### Example
```javascript
handler.addLabelLastValue();
```

### useRoundScale
----
#### :page_with_curl: Description
Configures the chart to use round scales on the y axis.

#### :bookmark_tabs: Parameters
Not available. <br>

#### :leftwards_arrow_with_hook: Return
Not available. <br>

#### :pencil2: Example:
```javascript
import { Controller, Inject } from '@philips/odin-ext';
import { WChart } from 'odin-utils/controllers';

@Controller({ domain: 'nicu/NicuAn', code: 1111111 })
export default class FluidBalanceChartWChart extends WChart {

  onLoad(schematics, handler) {
    handler.useRoundScale();
  }
}
```

# Histogram Chart
Histogram is a chart that lets you discover and show the frequency distribution of a set of continuous data. <br>
To make use of a Histogram chart, it's necessary to create a WChart object with the 'histogram' type in the object dictionary. <br>
By default this chart apply the Sturges' formula. <br>

## Axis
It this chart only necessary inform the axis X for your loading. <br>
The type of data accepted is date or number. <br>

## Bins
The bins is used for to specify intervals for the chart. By default this chart apply the Sturges' formula that divides values into bins using uniformly-spaced values. <br>
You can change in the properties in object dictionary how bins should be generated, applying this: <br>
On property typeInterval the values available are: time, hour, day, month and year. <br>
On property typeIntervalValue specify the number value. <br>
- Example: typeInterval: month and typeIntervalValue: 2. The bins is generated for month and tick label for every two month. <br>
For this case is works only for date type. <br>

# Stackedbar with slider
This chart type is displayed when there is a need to present a slider that interacts with the main character. It can be set to a certain range size.

## Settings
In schematics you need to set three pieces of information:

- __showSlider__: checkbox with value "S" or "N";
- __minRangeValue:__ minimum value for the slider to select;
- __minRangeValue__: maximum value for the slider to select; <br>
----

# Properties to configure the chart and its axes from the Backend:<br><br>

**maxTargetField** <br>
#### :page_with_curl: Description
this attribute allows the user to set the maximum target field<br>
data type the property accepts - String<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java
wChartSeriesListDTO.getChartProperties().setMaxTargetField("maxTargetField");
```
----
**minTargetField**<br>
#### :page_with_curl: Description
this attribute allows the user to set the minimum target field<br>
data type the property accepts - String<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setMinTargetField("minTargetField");
```
----
**maxTargetValue**<br>
#### :page_with_curl: Description
this attribute allows the user to set the maximum target value<br>
data type the property accepts - String<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setMaxTargetValue("maxTargetValue");
```
----
**minTargetValue**<br>
#### :page_with_curl: Description
this attribute allows the user to set the minimum target value<br>
data type the property accepts - String<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setMinTargetValue("minTargetValue");
```
----
**ieTargetType**<br>
#### :page_with_curl: Description
this attribute allows the user to set the type of the target<br>
data type the property accepts - String<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setIeTargetType("ieTargetType");
```
----
**StringType**<br>
#### :page_with_curl: Description
this attribute allows the user to set the type of the string<br>
data type the property accepts - String<br>
<br>**In the backend the user can set the same as the example:**
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setStringType("stringType");
```
----
**Type**<br>
#### :page_with_curl: Description
this attribute allows the user to set the type of the chart serie<br>
data type the property accepts - SerieType<br>
<br> - **There is an enum with the types available to the user: br/com/philips/tasy/dto/shared/wchart/SerieType.java**<br>
bar("B", "HB")<br>
pie("P")
timeline("TL")<br>
bar("B", "HB")<br>
pie("P")<br>
timeline("TL")<br>
gauge("G")<br>
line("L", "HL")<br>
area("A", "HA")<br>
spline("LS")<br>
donut("DN")<br>
halfdonut("HDN")<br>
scatter("D")<br>
bubble("BB")<br>
histogram("HST")

  **In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setSerieType(SerieType.HST);
```
----
**Title**<br>
#### :page_with_curl: Description
this attribute allows the user to set the title for the chart<br>
data type the property accepts - String<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setTitle("title");
```
----
**axisXTitle**<br>
#### :page_with_curl: Description
this attribute allows the user to set the title for the axis X<br>
data type the property accepts - String<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setAxisXTitle("axisXTitle");
```
----
**axisYTitle**<br>
#### :page_with_curl: Description
this attribute allows the user to set the title for the axis Y<br>
data type the property accepts - String<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setAxisYTitle("axisYTitle");
```
----
**axisY2Title**<br>
#### :page_with_curl: Description
this attribute allows the user to set the title for the axis Y2<br>
data type the property accepts - String<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setAxisY2Title("axisY2Title");
```
----
**axisXPositionTitle**<br>
#### :page_with_curl: Description
this attribute allows the user to define the position of the X axis title<br>
data type the property accepts - AxisXPositionTextEnum<br>
  <br> - **There is an enum with the positions available to the user: br/com/philips/tasy/dto/shared/wchart/AxisXPositionTextEnum.java**<br>
INNER_RIGHT("inner-right")<br>
INNER_CENTER("inner-center")<br>
INNER_LEFT("inner-left")<br>
OUTER_RIGHT("outer-right")<br>
OUTER_CENTER("outer-center")<br>
OUTER_LEFT("outer-left")<br>
 **In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setAxisXPositionTitle(AxisXPositionTextEnum.OUTER_RIGHT);
```
----
**axisYPositionTitle**<br>
#### :page_with_curl: Description
this attribute allows the user to define the position of the Y axis title<br>
data type the property accepts - AxisYPositionTextEnum<br>
  <br> - **There is an enum with the positions available to the user: br/com/philips/tasy/dto/shared/wchart/AxisYPositionTextEnum.java**<br>
INNER_TOP("inner-top")<br>
INNER_MIDDLE("inner-middle")<br>
INNER_BOTTOM("inner-bottom")<br>
OUTER_TOP("outer-top")<br>
OUTER_MIDDLE("outer-middle")<br>
OUTER_BOTTOM("outer-bottom")<br>
 **In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setAxisYPositionTitle(AxisYPositionTextEnum.OUTER_TOP);
```
----
**axisXType**<br>
#### :page_with_curl: Description
this attribute allows the user to define the type of the X axis<br>
data type the property accepts - AxisXType<br>
  <br> - **There is an enum with the positions available to the user: br/com/philips/tasy/dto/shared/wchart/AxisXTypeEnum.java**<br>
TIMESERIES("timeseries")<br>
CATEGORY("category")<br>
INDEXED("indexed")<br>
 **In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setAxisXType(AxisXTypeEnum.TIMESERIES);
```
----
**axisYType**<br>
#### :page_with_curl: Description
this attribute allows the user to define the type of the Y axis<br>
data type the property accepts - AxisYType<br>
  <br> - **There is an enum with the positions available to the user: br/com/philips/tasy/dto/shared/wchart/AxisYTypeEnum.java**<br>
LINEAR("linear")<br>
TIME("time")<br>
TIMESERIES("timeseries")<br>
LOG("log")<br>
 **In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setAxisYType(AxisYTypeEnum.LINEAR);
```
----
**innerAxisY**<br>
#### :page_with_curl: Description
this attribute allows the user define if want to set the inner axis Y<br>
data type the property accepts - Boolean<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setInnerAxisY(true);
```
----
**innerAxisY2**<br>
#### :page_with_curl: Description
this attribute allows the user define if want to set the inner axis Y2<br>
data type the property accepts - Boolean<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setInnerAxisY2(true);
```
----
**tickValueAxisX**<br>
#### :page_with_curl: Description
this attribute allows the user to tick values for axis X<br>
data type the property accepts - ArrayList<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setTickValueAxisX(new ArrayList<>(Arrays.asList("0","10","15","30","40")));
```
----
**tickValueAxisY**<br>
#### :page_with_curl: Description
this attribute allows the user to tick values for axis Y<br>
data type the property accepts - ArrayList<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setTickValueAxisY(new ArrayList<>(Arrays.asList("0","10","15","30","40")));
```
----
**tickValueAxisY2**<br>
#### :page_with_curl: Description
this attribute allows the user to tick values for axis Y2<br>
data type the property accepts - ArrayList<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setTickValueAxisY2(new ArrayList<>(Arrays.asList("0","10","15","30","40")));
```
----
**showGridX**<br>
#### :page_with_curl: Description
this attribute allows the user define if want to show the grid´s at the axis X<br>
data type the property accepts - Boolean<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setShowGridX(true);
```
----
**showGridY**<br>
#### :page_with_curl: Description
this attribute allows the user define if want to show the grid´s at the axis Y<br>
data type the property accepts - Boolean<br>
<br>**In the backend the user can set this propierty**
#### :pencil2: Example
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setShowGridY(true);
```
----
**linesGridX (WChartGridLineDTO)**<br>
#### :page_with_curl: Description
this attribute allows the user define if want to set and show the lines at the axis X<br>
data type the property accepts - (WChartGridLineDTO)<br>
<br> **linesGridY (WChartGridLineDTO)**
#### :page_with_curl: Description
this attribute allows the user define if want to set and show the lines at the axis Y<br>
data type the property accepts - (WChartGridLineDTO)<br>
<br>**In the backend the user can create a method to fill X axis lines array and Y axis lines array**
#### :pencil2: Example
```java I'm tab B
 private void fillArrayLines(ArrayList<WChartGridLineDTO> arrayListLinesX, ArrayList<WChartGridLineDTO> arrayListLinesY) {

  // X Lines//
  WChartGridLineDTO wChartGridLineDTOX1 = new WChartGridLineDTO();
        wChartGridLineDTOX1.setCssClass("class1");
        wChartGridLineDTOX1.setPosition(GridAxesPositionTextEnum.END);
        wChartGridLineDTOX1.setText("LINE1");
        wChartGridLineDTOX1.setValue(0);

        WChartGridLineDTO wChartGridLineDTOX2 = new WChartGridLineDTO();
        wChartGridLineDTOX2.setCssClass("class1");
        wChartGridLineDTOX2.setPosition(GridAxesPositionTextEnum.END);
        wChartGridLineDTOX2.setText("LINE2");
        wChartGridLineDTOX2.setValue(5);

        WChartGridLineDTO wChartGridLineDTOX3 = new WChartGridLineDTO();
        wChartGridLineDTOX3.setCssClass("class1");
        wChartGridLineDTOX3.setPosition(GridAxesPositionTextEnum.END);
        wChartGridLineDTOX3.setText("LINE3");
        wChartGridLineDTOX3.setValue(10);

        arrayListLinesX.add(wChartGridLineDTOX1);
        arrayListLinesX.add(wChartGridLineDTOX2);
        arrayListLinesX.add(wChartGridLineDTOX3);

    // Y Lines//
    WChartGridLineDTO wChartGridLineDTOY1 = new WChartGridLineDTO();
        wChartGridLineDTOY1.setCssClass("class2");
        wChartGridLineDTOY1.setPosition(GridAxesPositionTextEnum.END);
        wChartGridLineDTOY1.setText("LINE1");
        wChartGridLineDTOY1.setValue(45);

        WChartGridLineDTO wChartGridLineDTOY2 = new WChartGridLineDTO();
        wChartGridLineDTOY2.setCssClass("class2");
        wChartGridLineDTOY2.setPosition(GridAxesPositionTextEnum.END);
        wChartGridLineDTOY2.setText("LINE2");
        wChartGridLineDTOY2.setValue(55);

        WChartGridLineDTO wChartGridLineDTOY3 = new WChartGridLineDTO();
        wChartGridLineDTOY3.setCssClass("class2");
        wChartGridLineDTOY3.setPosition(GridAxesPositionTextEnum.END);
        wChartGridLineDTOY3.setText("LINE3");
        wChartGridLineDTOY3.setValue(66);

        arrayListLinesY.add(wChartGridLineDTOY1);
        arrayListLinesY.add(wChartGridLineDTOY2);
        arrayListLinesY.add(wChartGridLineDTOY3);
 };
```
   **Then the user can call the fillArrayLines method**<br>
#### :pencil2: Example
```java I'm tab B
private void callArrayLines(wChartSeriesListDTO) {
        ArrayList<WChartGridLineDTO> arrayListLinesY = new ArrayList<>();
        ArrayList<WChartGridLineDTO> arrayListLinesX = new ArrayList<>();
        fillArrayLines(arrayListLinesX, arrayListLinesY);
        wChartSeriesListDTO.getChartProperties().setLinesGridX(arrayListLinesX);
        wChartSeriesListDTO.getChartProperties().setLinesGridY(arrayListLinesY);
```

# Evolution Chart <br>
In the evolution graph, it is necessary to transform the values, when the X-axis ruler is for years, to render the graph correctly.<br><br>
**Example:** <br>
if the month is 1 (January), it is necessary to transform it to 0.333..., to month placement is correct.<br>
To activate the rules mode on the x-axis, it is necessary to pass 2 parameters, one of which is mandatory,
<br>
valueX => valueX / 3; || valueX => (valueX / 12) * 4;<br>

**yearFinalScaleTickAxisX**<br>
#### :page_with_curl: Description
this parameter is necessary to know until what year the scale must be created (mandatory for active the ruler)<br>
data type the property accepts - (WChartGridLineDTO)<br>
#### :pencil2: Example<br>
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setYearFinalRulerTickAxisX(19);
```
------
**yearInitialScaleTickAxisX**<br>
#### :page_with_curl: Description
this parameter is used to say in which year it will start the axis scale, if the parameter is not passed, it will start at 0<br>
data type the property accepts - (WChartGridLineDTO)<br>
#### :pencil2: Example<br>
```java I'm tab B
wChartSeriesListDTO.getChartProperties().setYearInitialRulerTickAxisX(5);
```

# Line Chart JS - (Vital signs) <br>
The "LINE_CHART_JS" type chart basically has the same structure as the "LINE" type, but it was created so that we can customize the 'point styles'.</br>

To use the 'LINE_CHART_JS' type chart, we need to make the change via 'BACKEND', as this type of chart is a motivation of the 'LINE' type, so to use it we have to make some configurations and change the default 'Line' type.</br>


#### :warning: Note:
_The functionality of the chart types 'LINE' and 'LINE_CHART_JS' have practically the same behaviors.</br>
However, in order to customize the 'point styles', we had to use another graphics library and, for this reason, there may be a small visual or behavioral difference between them._


#### :pencil2: Examples: <br>

_Setting to change chart type to 'LINE_CHART_JS'_<br>
```java
WChartProperties chartProperties = new WChartProperties();
chartProperties.setType(SerieType.LINE_CHART_JS);
```

_Configuration to change point style, we have these options: 'CIRCLE', 'CROSS', 'CROSS_ROT', 'DASH', 'LINE', 'RECT', 'RECT_ROUNDED', 'RECT_ROT', 'STAR', 'TRIANGLE'_<br>

```java
WChartProperties chartProperties = new WChartProperties();
chartProperties.addPointsStyles(PointStyleEnum.CIRCLE);
```

#### Full Example:
```java
import br.com.philips.tasy.dto.shared.wchart.WChartProperties;
import br.com.philips.tasy.dto.shared.wchart.WChartSeriesDTO;
import br.com.philips.tasy.dto.shared.wchart.WChartSeriesValueDTO;
import br.com.wheb.action.wChart.AbstractWChartPostProcessingAction;
import br.com.wheb.annotations.NamedAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@NamedAction(name = "postProcessSeries", referedInterface = AbstractWChartPostProcessingAction.class)
public class TecTesF1WChartPostProcessingAction extends AbstractWChartPostProcessingAction {

    @Override
    public ArrayList<WChartSeriesDTO> build(ArrayList<WChartSeriesDTO> series, Integer dictionaryCode, Map<String, Object> params,
                                            ArrayList<HashMap> aditionalSeries, Map<String, Object> filterValues, WChartProperties chartProperties) {
        ArrayList<WChartSeriesValueDTO> values1stSerie = new ArrayList();
        ArrayList<WChartSeriesValueDTO> values2ndSerie = new ArrayList();
        ArrayList<WChartSeriesValueDTO> values3rdSerie = new ArrayList();
        ArrayList<WChartSeriesValueDTO> values4thSerie = new ArrayList();
        String[] dates = {
            "01/07/2018",
            "02/07/2018",
            "03/07/2018",
            "04/07/2018",
            "05/07/2018",
            "06/07/2018",
            "07/07/2018",
            "08/07/2018",
            "09/07/2018",
            "10/07/2018",
        };

        for (int i = 0; i < 10; i++) {
            values1stSerie.add(createValueDTO(dates[i],400 - 10 * i));
            values2ndSerie.add(createValueDTO(dates[i],50 * i));
            values3rdSerie.add(createValueDTO(dates[i],200 - 2 * i));
            if (i == 1) {
                values4thSerie.add(createValueDTO(dates[i], 0));
            } else {
                values4thSerie.add(createValueDTO(dates[i],100 * i - 75));
            }
        }

        for (WChartSeriesValueDTO value : values1stSerie) {
            series.get(0).getValues().add(value);
        }
        for (WChartSeriesValueDTO value : values2ndSerie) {
            series.get(1).getValues().add(value);
        }
        for (WChartSeriesValueDTO value : values3rdSerie) {
            series.get(2).getValues().add(value);
        }
        for (WChartSeriesValueDTO value : values4thSerie) {
            series.get(3).getValues().add(value);
        }

        // Configuration of chart properties 'LINE_CHART_JS'
        // Sets to type 'LINE_CHART_JS'
        chartProperties.setType(SerieType.LINE_CHART_JS);

        // Sets 'point style' of the FIRST series to type 'TRIANGLE'
        chartProperties.addPointsStyles(PointStyleEnum.TRIANGLE);

        // Sets 'point style' of the SECOND series to type 'STAR'
        chartProperties.addPointsStyles(PointStyleEnum.STAR);

        // Sets 'point style' of the THIRD series to type 'CIRCLE'
        chartProperties.addPointsStyles(PointStyleEnum.CIRCLE);

        // Sets 'point style' of the FOURTH series to type 'RECT_ROUNDED'
        chartProperties.addPointsStyles(PointStyleEnum.RECT_ROUNDED);

        return series;
    }

    private WChartSeriesValueDTO createValueDTO(Object axisX, Object axisY) {
        WChartSeriesValueDTO value = new WChartSeriesValueDTO();
        value.setPrediction(true);
        value.setValueAxisX(axisX);
        value.setValueAxisY(axisY);
        return value;
    }
}
```


# Input and output chart <br>
In the Input Output graph, it is necessary to pass one more value to the backend if it is necessary to make the jump in scale.<br><br>
In positive scale or negative scale
#### :pencil2: Example<br>
```java I'm tab B
ValuesBreakDTO valuesBreakDTO = new ValuesBreakDTO();

        ValueBreakDTO positive = new ValueBreakDTO();
        positive.setInitial(50.0);
        positive.setInterval(100.0);
        positive.setMax(40000.0);

        ValueBreakDTO negative = new ValueBreakDTO();
        negative.setInitial(-50.0);
        negative.setInterval(-100.0);
        negative.setMax(-40000.0);

        valuesBreakDTO.setNegative(negative);
        valuesBreakDTO.setPositive(positive);
        wChartSeriesListDTO.getChartProperties().setValuesBreak(valuesBreakDTO);
```

import {Controller} from 'stimulus';

import echarts from "echarts";

echarts.dataTool = require("echarts/extension/dataTool");

export default class extends Controller {
  connect() {
    // console.log('Stimulus connected!');
  }

  initialize() {
    // console.log('Stimulus initialized!');

    this.loadOverview();
  }

  loadOverview() {
    const domid = document.getElementById('overviewId');
    const myChart = echarts.init(domid);

    myChart.showLoading();

    $.get('/assets/data/mod.gexf', function (xml) {
      myChart.hideLoading();

      const graph = echarts.dataTool.gexf.parse(xml);
      const categories = [];
      for (let i = 0; i < 9; i++) {
        categories[i] = {
          name: '类目' + i
        };
      }
      graph.nodes.forEach(function (node) {
        node.itemStyle = null;
        node.value = node.symbolSize;
        node.symbolSize /= 1.2;
        node.label = {
          show: node.symbolSize >= 10
        };

        if (node.symbolSize >= 60) {
          node.label.fontSize = 16;
        } else if (node.symbolSize >= 20) {
          node.label.fontSize = 8;
        } else if (node.symbolSize >= 10) {
          node.label.fontSize = 6;
        }

        node.category = node.attributes.modularity_class;
        // console.log(node);
        node.y = (-650 - node.y);
      });

      const option = {
        title: {
          text: 'METIS Platform',
          top: 'bottom',
          left: 'right'
        },
        tooltip: {
          show: false,
        },
        legend: false,
        animationDuration: 1500,
        animationEasingUpdate: 'quinticInOut',
        series: [
          {
            name: 'METIS',
            type: 'graph',
            layout: 'none',
            data: graph.nodes,
            links: graph.links,
            categories: categories,
            roam: true,
            focusNodeAdjacency: true,
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 1,
              shadowBlur: 10,
              shadowColor: 'rgba(0, 0, 0, 0.3)'
            },
            lineStyle: {
              color: 'source',
              curveness: 0.3
            },
            emphasis: {
              lineStyle: {
                width: 10
              }
            }
          }
        ]
      };

      myChart.setOption(option);
    }, 'xml');
  }
}

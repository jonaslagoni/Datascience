#!/bin/bash

#Vizualizer
ag --output ./visualizer-docs visualizer.json html
ag --output ../visualizer --templates ./templates visualizer.json javascript-class-template

# spark submitter
ag --output ./spark-submitter-docs spark-submitter.json html
ag --output ../spark-submitter --templates ./templates spark-submitter.json java-class-template

# Warehouse
ag --output ./warehouse-docs warehouse.json html
ag --output ../data-warehouse --templates ./templates warehouse.json javascript-class-template

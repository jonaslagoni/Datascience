#!/bin/bash

ag --output ./visualizer-docs visualizer.json html
ag --output ../spark-submitter --templates ./templates spark-submitter.json java-class-template
ag --output ./spark-submitter-docs spark-submitter.json html
ag --output ./warehouse-docs warehouse.json html

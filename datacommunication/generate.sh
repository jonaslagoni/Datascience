#!/bin/bash

ag --output ./visualizer visualizer.yml html
ag --output ./spark-submitter spark-submitter.yml html
ag --output ./warehouse warehouse.yml html

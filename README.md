# Country Land Route
Application exposes one endpoint which calculates land route from one country to another using Breadth-First Search algorithm and [countries repository](https://github.com/mledoze/countries) as data source. 

# Requirements
- [Docker](https://www.docker.com/)

# Quick installation
Command line:
```
docker compose up
```
# Usage
1. Run application
2. Go to http://localhost:8080/routing/POL/FRA


# Known issues
- Poland's neighbour Kaliningrad is treated as if whole Russia was a neighbour, meaning it's possible to get to China just through Russia
- Strait of Gibraltar is treated as a possible land route to Africa
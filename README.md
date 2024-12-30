# Jasper Report Generator Framework

**Jasper Report Generator** is a simple project for generating dynamic reports using **JasperReports**. It enables developers to effortlessly create reports by extending a base class `XmlDto`, where you define custom SQL queries and configure report fields. The framework automatically handles the data retrieval and presentation, allowing you to focus on defining the structure of the report itself. This setup ensures that you can generate various types of reports in different formats (PDF, Excel, HTML, etc.) with minimal effort.

## What is this Project About?

This project is designed to simplify the process of creating and generating reports using the popular JasperReports library. Traditionally, creating a report requires manual configuration of complex layouts and data sources, but with this framework, you can define the report's structure and data retrieval in just a few simple steps.

The main idea behind this framework is that by extending the `XmlDto` class, you can easily create custom reports by:
- Writing an SQL query to fetch the desired data.
- Mapping the query results to fields in the report.
- Configuring summary fields to calculate totals or averages.
- Generating reports in popular formats such as PDF, Excel, or HTML.

## How It Works

The framework allows you to focus on defining your report's query and fields, while it takes care of the rest. Here's how the process works:

1. **Extend `XmlDto`**: You create a new class by extending the `XmlDto` class, which provides the basic functionality to define your report.
2. **Define SQL Query**: You write a SQL query that fetches the necessary data from your database.
3. **Map Fields**: You define the fields to display in the report (e.g., subject, term, marks, etc.).
4. **Set Up Summaries**: If needed, you can set up summary fields to calculate totals or averages (e.g., total marks).
5. **Generate Report**: Once everything is configured, the framework automatically processes the data and generates the report in your preferred format.

## How to Generate a Report

To generate a report using this framework, follow these simple steps:

1. **Clone the Repository**

2. **Configure Database Connection**

3. **Create a Custom Report**:
   - Create a new class that extends `XmlDto`.
   - Define the SQL query to fetch the data for the report.
   - Set the fields and summary expressions for your report.

4. **Run the Application**

5. **Generate the Report**:
   - Once the application is running, the report will be generated automatically based on the configuration you've set. You can choose the desired format (e.g., PDF, Excel, etc.).

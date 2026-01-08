# 🌦️ WeatherApp Pro

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://github.com/sanjithms/WeatherApp)
[![JS Version](https://img.shields.io/badge/JavaScript-ES6%2B-F7DF1E.svg)](https://www.javascript.com/)

An intuitive, high-performance weather dashboard that bridges the gap between complex meteorological data and user-friendly design. Built with a focus on speed, responsiveness, and clean UI/UX.

---

## 📖 Project Overview

The **WeatherApp** is designed to provide users with an immediate snapshot of environmental conditions. By leveraging the **OpenWeatherMap API**, the application handles asynchronous data fetching to display real-time metrics for over 200,000 cities worldwide.

### Why this app?
* **Minimal Latency:** Optimized API calls for fast loading.
* **Intuitive UI:** Clean layout inspired by modern glassmorphism trends.
* **Accuracy:** Pulls data from global meteorological stations.

---

## 🚀 Key Features

* **Smart Search:** Real-time search functionality with error handling for invalid city names.
* **Weather Metrics:** * Temperature (Celsius/Fahrenheit)
    * Atmospheric Humidity levels
    * Wind Velocity and Direction
    * "Feels Like" thermal sensation
* **Adaptive Backgrounds:** The UI theme shifts dynamically based on weather conditions (e.g., cool tones for rain, warm tones for clear skies).
* **Geolocation Support:** Automatically detects the user's current location (optional feature).

---

## 🛠️ Technical Stack & Architecture

This project follows a modular frontend architecture:

| Component | Technology | Description |
|:--- |:--- |:--- |
| **Logic** | JavaScript (ES6) | Handles API fetch, JSON parsing, and DOM manipulation. |
| **Styling** | CSS3 / Flexbox | Implements a mobile-first, responsive grid system. |
| **Structure** | HTML5 | Semantic markup for better SEO and accessibility. |
| **Data Source** | OpenWeather API | RESTful API providing current weather data via JSON. |

### Data Flow Diagram


---

## 📸 Output & Visuals

### Desktop & Mobile Interface
Below are the visual snapshots of the application across different device profiles:

| Platform | Interface Preview |
|:--- |:--- |
| **Desktop Dashboard** | <img src="./screenshots/desktop.png" width="500" Alt="Desktop View"> |
| **Mobile Experience** | <img src="./screenshots/mobile.png" width="250" Alt="Mobile View"> |

### 🖼️ Detailed Screenshot Gallery
> To see the app in different states, view the folder: `/screenshots`

#### 1. Clear Weather State
![Clear State](./screenshots/main_output.png)
*Description: The UI displays a vibrant sun icon and high-contrast text for outdoor visibility.*

#### 2. Storm/Rain State
![Rain State](./screenshots/search_output.png)
*Description: The app shifts to a darker, muted palette with rain-specific animations.*

---

## ⚙️ Installation & Developer Setup

Follow these steps to set up a local development environment:

1. **Clone the Project**
   ```bash
   git clone [https://github.com/sanjithms/WeatherApp.git](https://github.com/sanjithms/WeatherApp.git)

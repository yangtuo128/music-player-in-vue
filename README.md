# Music Player in Vue

A modern music player application built with Vue.js, featuring a clean and intuitive interface.

## Features

- 🎵 Music playback with controls (play, pause, next, previous, shuffle, repeat)
- 📱 Responsive design for mobile and desktop devices
- 🔍 Search functionality to find songs and artists
- 📊 Top charts and trending music
- 📝 User playlist management
- 👤 User authentication and personal center
- 🎨 Beautiful and modern UI design

## Tech Stack

- **Frontend**: Vue.js 2, Vue Router, Vuex, Less
- **Backend**: Java Spring Boot, Spring Data JPA, MySQL
- **HTTP Client**: Axios

## Project Structure

```
.
├── src/
│   ├── api/              # API service modules
│   ├── assets/           # Static assets (images, icons)
│   ├── components/       # Reusable components
│   ├── store/            # Vuex state management
│   ├── views/            # Page components
│   │   ├── Home/         # Home page
│   │   ├── LeaderBoard/  # Top charts page
│   │   ├── Login/        # Login page
│   │   ├── My/           # My music page
│   │   ├── PlayControl/  # Music player page
│   │   ├── PlayListDetail/ # Playlist detail page
│   │   ├── RecentPlay/   # Recent plays page
│   │   ├── Register/     # Register page
│   │   └── Search/       # Search page
│   ├── App.vue           # Root component
│   ├── main.js           # Application entry
│   └── router.js         # Router configuration
├── backend/              # Java backend service
├── public/              # Public resources
├── package.json         # Dependencies and scripts
├── vue.config.js        # Vue CLI configuration
└── README.md            # Project documentation
```

## Installation

1. Install dependencies:

```bash
npm install
```

2. Set up the backend service (see `backend/README.md` for instructions).

3. Update the API base URL in `src/api/api.js` if needed.

## Running the Application

### Development Mode

```bash
npm run serve
```

The application will be available at `http://localhost:8080` (or the port specified by Vue CLI).

### Production Build

```bash
npm run build
```

The optimized production build will be generated in the `dist` directory.

## Configuration

### API Configuration

Edit `src/api/api.js` to configure the backend API base URL:

```javascript
export const baseURL = 'http://localhost:8080/api';
```

### Router Configuration

Edit `src/router.js` to add or modify routes:

```javascript
const router = new Router({
  routes: [
    // Add your routes here
  ],
});
```

### State Management

Edit `src/store/` directory to manage application state with Vuex:

- `state.js` - Define state variables
- `mutations.js` - Define state mutations
- `actions.js` - Define actions that commit mutations
- `getters.js` - Define getters for computed state

## Components

### Main Components

- `MyAudio.vue` - Music player component
- `Nav.vue` - Top navigation bar
- `SongList.vue` - Song list component
- `Spin.vue` - Loading indicator component

### Page Components

- `Home.vue` - Home page with recommended music
- `LeaderBoard.vue` - Top charts and trending music
- `Login.vue` - User login page
- `Register.vue` - User registration page
- `Profile.vue` - User personal center

## API Services

The application communicates with the backend through API services located in `src/api/`:

- `Login/` - Authentication services
- `My/` - User profile and playlist services
- `Search/` - Search services
- `LeaderBoard/` - Top charts services

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Make your changes
4. Commit your changes (`git commit -am 'Add some feature'`)
5. Push to the branch (`git push origin feature/your-feature`)
6. Submit a pull request

## License

MIT
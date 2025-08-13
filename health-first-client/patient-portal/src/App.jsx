import { useState } from 'react';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import { CssBaseline, Container, Box } from '@mui/material';
import LoginForm from './components/LoginForm';
import RegistrationForm from './components/RegistrationForm';
import Dashboard from './components/Dashboard';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#42a5f5',
    },
    background: {
      default: '#f5f5f5',
      paper: '#ffffff',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    h4: {
      fontWeight: 600,
    },
  },
  components: {
    MuiTextField: {
      styleOverrides: {
        root: {
          '& .MuiOutlinedInput-root': {
            '&:hover fieldset': {
              borderColor: '#1976d2',
            },
            '&.Mui-focused fieldset': {
              borderColor: '#1976d2',
            },
          },
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          borderRadius: 8,
          padding: '12px 24px',
          fontSize: '1rem',
          fontWeight: 500,
        },
      },
    },
  },
});

function App() {
  const [currentView, setCurrentView] = useState('login'); // 'login', 'registration', 'dashboard'

  const handleLoginSuccess = () => {
    setCurrentView('dashboard');
  };

  const handleRegistrationSuccess = () => {
    setCurrentView('login');
  };

  const handleShowRegistration = () => {
    setCurrentView('registration');
  };

  const handleBackToLogin = () => {
    setCurrentView('login');
  };

  const handleLogout = () => {
    setCurrentView('login');
  };

  const renderCurrentView = () => {
    switch (currentView) {
      case 'login':
        return (
          <LoginForm 
            onLoginSuccess={handleLoginSuccess}
            onShowRegistration={handleShowRegistration}
          />
        );
      case 'registration':
        return (
          <RegistrationForm 
            onRegistrationSuccess={handleRegistrationSuccess}
            onBackToLogin={handleBackToLogin}
          />
        );
      case 'dashboard':
        return <Dashboard onLogout={handleLogout} />;
      default:
        return (
          <LoginForm 
            onLoginSuccess={handleLoginSuccess}
            onShowRegistration={handleShowRegistration}
          />
        );
    }
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Box sx={{ 
        minHeight: '100vh', 
        background: currentView === 'dashboard' 
          ? '#f5f5f5' 
          : 'linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%)',
        display: 'flex',
        alignItems: currentView === 'dashboard' ? 'flex-start' : 'center',
        justifyContent: 'center',
        padding: currentView === 'dashboard' ? 0 : 2
      }}>
        <Container maxWidth={currentView === 'dashboard' ? false : 'sm'}>
          {renderCurrentView()}
        </Container>
      </Box>
    </ThemeProvider>
  );
}

export default App

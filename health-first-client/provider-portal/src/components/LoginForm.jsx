import { useState } from 'react';
import { 
  TextField,
  FormControlLabel,
  Checkbox,
  Link,
  Alert,
  CircularProgress,
  InputAdornment,
  IconButton,
  Paper,
  Fade,
  Slide,
  Zoom,
  Box,
  Typography,
  Button,
  Divider
} from '@mui/material';
import { 
  LocalHospital, 
  Visibility, 
  VisibilityOff, 
  Email, 
  Lock,
  Phone,
  ErrorOutline,
  CheckCircleOutline,
  Person
} from '@mui/icons-material';

const LoginForm = ({ onLoginSuccess, onBackToLogin, onGuestLogin }) => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    rememberMe: false,
  });
  
  const [errors, setErrors] = useState({});
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [loginStatus, setLoginStatus] = useState(null);
  const [errorMessage, setErrorMessage] = useState('');

  // Validation functions
  const validateEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const validatePhone = (phone) => {
    const phoneRegex = /^[+]?[1-9][\d]{0,15}$/;
    return phoneRegex.test(phone.replace(/\s/g, ''));
  };

  const validatePassword = (password) => {
    return password.length >= 6;
  };

  const validateField = (name, value) => {
    switch (name) {
      case 'email':
        if (!value) return 'Email is required';
        if (!validateEmail(value) && !validatePhone(value)) {
          return 'Please enter a valid email or phone number';
        }
        return '';
      case 'password':
        if (!value) return 'Password is required';
        if (!validatePassword(value)) {
          return 'Password must be at least 6 characters long';
        }
        return '';
      default:
        return '';
    }
  };

  const handleInputChange = (e) => {
    const { name, value, checked } = e.target;
    const fieldValue = name === 'rememberMe' ? checked : value;
    
    setFormData(prev => ({
      ...prev,
      [name]: fieldValue
    }));

    // Real-time validation
    if (name !== 'rememberMe') {
      const error = validateField(name, fieldValue);
      setErrors(prev => ({
        ...prev,
        [name]: error
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Validate all fields
    const newErrors = {};
    Object.keys(formData).forEach(key => {
      if (key !== 'rememberMe') {
        const error = validateField(key, formData[key]);
        if (error) newErrors[key] = error;
      }
    });

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    setIsLoading(true);
    setLoginStatus(null);
    setErrorMessage('');

    // Mock login with specific credentials
    try {
      await new Promise(resolve => setTimeout(resolve, 1500));
      
      // Mock credentials check
      if (formData.email === 'doctor@healthcare.com' && formData.password === 'password123') {
        setLoginStatus('success');
        setTimeout(() => {
          onLoginSuccess();
        }, 1500);
      } else {
        throw new Error('Invalid email or password');
      }

    } catch (error) {
      setLoginStatus('error');
      setErrorMessage(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  const isFormValid = () => {
    return formData.email && formData.password && 
           !errors.email && !errors.password;
  };

  return (
    <Slide direction="up" in={true} timeout={800}>
      <Paper elevation={24} sx={{
        borderRadius: 3,
        padding: { xs: 3, sm: 4 },
        background: 'rgba(255, 255, 255, 0.98)',
        backdropFilter: 'blur(20px)',
        border: '1px solid rgba(255, 255, 255, 0.3)',
        maxWidth: 450,
        width: '100%',
        mx: 'auto'
      }}>
        {/* Header */}
        <Box sx={{ textAlign: 'center', mb: 4 }}>
          <Fade in={true} timeout={1200}>
            <Box sx={{ mb: 2 }}>
              <LocalHospital sx={{ 
                fontSize: 60, 
                color: 'primary.main',
                filter: 'drop-shadow(0 4px 8px rgba(0,0,0,0.1))'
              }} />
            </Box>
          </Fade>
          <Fade in={true} timeout={1400}>
            <Typography variant="h4" component="h1" gutterBottom sx={{ 
              fontWeight: 700,
              background: 'linear-gradient(45deg, #2e7d32, #4caf50)',
              backgroundClip: 'text',
              WebkitBackgroundClip: 'text',
              WebkitTextFillColor: 'transparent'
            }}>
              Provider Portal
            </Typography>
          </Fade>
          <Fade in={true} timeout={1600}>
            <Typography variant="body1" color="text.secondary">
              Sign in to access your healthcare dashboard
            </Typography>
          </Fade>
        </Box>

        {/* Status Messages */}
        {loginStatus === 'success' && (
          <Fade in={true}>
            <Alert 
              icon={<CheckCircleOutline />} 
              severity="success" 
              sx={{ mb: 3, borderRadius: 2 }}
            >
              Login successful! Redirecting to dashboard...
            </Alert>
          </Fade>
        )}

        {loginStatus === 'error' && (
          <Fade in={true}>
            <Alert 
              icon={<ErrorOutline />} 
              severity="error" 
              sx={{ mb: 3, borderRadius: 2 }}
            >
              {errorMessage}
            </Alert>
          </Fade>
        )}

        {/* Login Form */}
        <Fade in={true} timeout={1800}>
          <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
            <TextField
              fullWidth
              label="Email or Phone Number"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              error={!!errors.email}
              helperText={errors.email}
              margin="normal"
              variant="outlined"
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    {formData.email.includes('@') ? <Email color="action" /> : <Phone color="action" />}
                  </InputAdornment>
                ),
              }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  transition: 'all 0.3s ease',
                  '&:hover': {
                    transform: 'translateY(-2px)',
                    boxShadow: '0 4px 12px rgba(46, 125, 50, 0.15)',
                  },
                },
              }}
            />

            <TextField
              fullWidth
              label="Password"
              name="password"
              type={showPassword ? 'text' : 'password'}
              value={formData.password}
              onChange={handleInputChange}
              error={!!errors.password}
              helperText={errors.password}
              margin="normal"
              variant="outlined"
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <Lock color="action" />
                  </InputAdornment>
                ),
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      onClick={() => setShowPassword(!showPassword)}
                      edge="end"
                      sx={{ color: 'action.active' }}
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  transition: 'all 0.3s ease',
                  '&:hover': {
                    transform: 'translateY(-2px)',
                    boxShadow: '0 4px 12px rgba(46, 125, 50, 0.15)',
                  },
                },
              }}
            />

            <Box sx={{ 
              display: 'flex', 
              justifyContent: 'space-between', 
              alignItems: 'center',
              mt: 2,
              mb: 3
            }}>
              <FormControlLabel
                control={
                  <Checkbox
                    name="rememberMe"
                    checked={formData.rememberMe}
                    onChange={handleInputChange}
                    color="primary"
                  />
                }
                label="Remember me"
              />
              <Link
                href="#"
                variant="body2"
                sx={{
                  textDecoration: 'none',
                  color: 'primary.main',
                  fontWeight: 500,
                  '&:hover': {
                    textDecoration: 'underline',
                  },
                }}
              >
                Forgot password?
              </Link>
            </Box>

            <Button
              type="submit"
              fullWidth
              variant="contained"
              disabled={!isFormValid() || isLoading}
              sx={{
                height: 56,
                fontSize: '1.1rem',
                fontWeight: 600,
                background: 'linear-gradient(135deg, #1976d2 0%, #42a5f5 100%)',
                boxShadow: '0 4px 12px rgba(25, 118, 210, 0.3)',
                transition: 'all 0.3s ease',
                '&:hover': {
                  background: 'linear-gradient(135deg, #1565c0 0%, #1976d2 100%)',
                  boxShadow: '0 6px 20px rgba(25, 118, 210, 0.4)',
                  transform: 'translateY(-2px)',
                },
                '&:disabled': {
                  background: '#e0e0e0',
                  color: '#9e9e9e',
                  boxShadow: 'none',
                  transform: 'none',
                },
              }}
            >
              {isLoading ? (
                <CircularProgress size={24} color="inherit" />
              ) : (
                'Sign In'
              )}
            </Button>
          </Box>
        </Fade>

        {/* Guest Login Section */}
        <Fade in={true} timeout={2200}>
          <Box sx={{ mt: 3 }}>
            <Divider sx={{ my: 2 }}>
              <Typography variant="body2" color="text.secondary">
                OR
              </Typography>
            </Divider>
            
            <Button
              fullWidth
              variant="outlined"
              onClick={onGuestLogin}
              startIcon={<Person />}
              sx={{
                height: 48,
                fontSize: '1rem',
                fontWeight: 500,
                borderColor: '#1976d2',
                color: '#1976d2',
                transition: 'all 0.3s ease',
                '&:hover': {
                  borderColor: '#1565c0',
                  backgroundColor: 'rgba(25, 118, 210, 0.04)',
                  transform: 'translateY(-2px)',
                  boxShadow: '0 4px 12px rgba(25, 118, 210, 0.2)',
                },
              }}
            >
              Continue as Guest
            </Button>
          </Box>
        </Fade>

        {/* Footer */}
        <Fade in={true} timeout={2000}>
          <Box sx={{ textAlign: 'center', mt: 4 }}>
            <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
              Need help? Contact{' '}
              <Link
                href="#"
                sx={{
                  color: 'primary.main',
                  textDecoration: 'none',
                  fontWeight: 500,
                  '&:hover': {
                    textDecoration: 'underline',
                  },
                }}
              >
                technical support
              </Link>
            </Typography>
            <Typography variant="body2" color="text.secondary">
              Don't have an account?{' '}
              <Button
                onClick={onBackToLogin}
                sx={{
                  color: 'primary.main',
                  textDecoration: 'none',
                  fontWeight: 500,
                  p: 0,
                  minWidth: 'auto',
                  '&:hover': {
                    textDecoration: 'underline',
                    backgroundColor: 'transparent',
                  },
                }}
              >
                Register here
              </Button>
            </Typography>
          </Box>
        </Fade>
      </Paper>
    </Slide>
  );
};

export default LoginForm; 
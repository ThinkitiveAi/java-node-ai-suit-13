import { useState } from 'react';
import { 
  TextField,
  Button,
  Paper,
  Typography,
  Box,
  Grid,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Alert,
  CircularProgress,
  InputAdornment,
  IconButton,
  Fade,
  Slide,
  Zoom,
  Stepper,
  Step,
  StepLabel,
  Card,
  CardContent,
  Divider
} from '@mui/material';
import { 
  LocalHospital, 
  Person, 
  Email, 
  Phone, 
  Business, 
  LocationOn,
  Lock,
  Visibility,
  VisibilityOff,
  CheckCircleOutline,
  ErrorOutline,
  School,
  Work,
  Home
} from '@mui/icons-material';

const specializations = [
  'Cardiology',
  'Dermatology',
  'Endocrinology',
  'Gastroenterology',
  'General Practice',
  'Gynecology',
  'Neurology',
  'Oncology',
  'Ophthalmology',
  'Orthopedics',
  'Pediatrics',
  'Psychiatry',
  'Radiology',
  'Surgery',
  'Urology'
];

const steps = ['Personal Information', 'Professional Details', 'Clinic Address', 'Account Security'];

const RegistrationForm = ({ onRegistrationSuccess, onBackToLogin }) => {
  const [activeStep, setActiveStep] = useState(0);
  const [formData, setFormData] = useState({
    // Personal Information
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    
    // Professional Information
    specialization: '',
    licenseNumber: '',
    yearsExperience: '',
    
    // Clinic Address
    streetAddress: '',
    city: '',
    state: '',
    zipCode: '',
    
    // Account Security
    password: '',
    confirmPassword: ''
  });
  
  const [errors, setErrors] = useState({});
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [registrationStatus, setRegistrationStatus] = useState(null);
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

  const validateLicenseNumber = (license) => {
    const licenseRegex = /^[A-Z0-9]{6,12}$/;
    return licenseRegex.test(license);
  };

  const validatePassword = (password) => {
    return password.length >= 8 && 
           /[A-Z]/.test(password) && 
           /[a-z]/.test(password) && 
           /[0-9]/.test(password) &&
           /[!@#$%^&*]/.test(password);
  };

  const validateZipCode = (zip) => {
    const zipRegex = /^\d{5}(-\d{4})?$/;
    return zipRegex.test(zip);
  };

  const validateField = (name, value) => {
    switch (name) {
      case 'firstName':
      case 'lastName':
        if (!value) return `${name === 'firstName' ? 'First' : 'Last'} name is required`;
        if (value.length < 2) return `${name === 'firstName' ? 'First' : 'Last'} name must be at least 2 characters`;
        if (value.length > 50) return `${name === 'firstName' ? 'First' : 'Last'} name must be less than 50 characters`;
        return '';
      
      case 'email':
        if (!value) return 'Email is required';
        if (!validateEmail(value)) return 'Please enter a valid email address';
        return '';
      
      case 'phone':
        if (!value) return 'Phone number is required';
        if (!validatePhone(value)) return 'Please enter a valid phone number';
        return '';
      
      case 'specialization':
        if (!value) return 'Specialization is required';
        if (value.length < 3) return 'Specialization must be at least 3 characters';
        if (value.length > 100) return 'Specialization must be less than 100 characters';
        return '';
      
      case 'licenseNumber':
        if (!value) return 'Medical license number is required';
        if (!validateLicenseNumber(value)) return 'License number must be 6-12 alphanumeric characters';
        return '';
      
      case 'yearsExperience': {
        if (!value) return 'Years of experience is required';
        const years = parseInt(value);
        if (isNaN(years) || years < 0 || years > 50) return 'Years of experience must be between 0 and 50';
        return '';
      }
      
      case 'streetAddress':
        if (!value) return 'Street address is required';
        if (value.length > 200) return 'Street address must be less than 200 characters';
        return '';
      
      case 'city':
        if (!value) return 'City is required';
        if (value.length > 100) return 'City must be less than 100 characters';
        return '';
      
      case 'state':
        if (!value) return 'State/Province is required';
        if (value.length > 50) return 'State/Province must be less than 50 characters';
        return '';
      
      case 'zipCode':
        if (!value) return 'ZIP/Postal code is required';
        if (!validateZipCode(value)) return 'Please enter a valid ZIP/Postal code';
        return '';
      
      case 'password':
        if (!value) return 'Password is required';
        if (!validatePassword(value)) {
          return 'Password must be at least 8 characters with uppercase, lowercase, number, and special character';
        }
        return '';
      
      case 'confirmPassword':
        if (!value) return 'Please confirm your password';
        if (value !== formData.password) return 'Passwords do not match';
        return '';
      
      default:
        return '';
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));

    // Real-time validation
    const error = validateField(name, value);
    setErrors(prev => ({
      ...prev,
      [name]: error
    }));
  };

  const validateStep = (step) => {
    const newErrors = {};
    let isValid = true;

    switch (step) {
      case 0: // Personal Information
        ['firstName', 'lastName', 'email', 'phone'].forEach(field => {
          const error = validateField(field, formData[field]);
          if (error) {
            newErrors[field] = error;
            isValid = false;
          }
        });
        break;
      
      case 1: // Professional Information
        ['specialization', 'licenseNumber', 'yearsExperience'].forEach(field => {
          const error = validateField(field, formData[field]);
          if (error) {
            newErrors[field] = error;
            isValid = false;
          }
        });
        break;
      
      case 2: // Clinic Address
        ['streetAddress', 'city', 'state', 'zipCode'].forEach(field => {
          const error = validateField(field, formData[field]);
          if (error) {
            newErrors[field] = error;
            isValid = false;
          }
        });
        break;
      
      case 3: // Account Security
        ['password', 'confirmPassword'].forEach(field => {
          const error = validateField(field, formData[field]);
          if (error) {
            newErrors[field] = error;
            isValid = false;
          }
        });
        break;
    }

    setErrors(prev => ({ ...prev, ...newErrors }));
    return isValid;
  };

  const handleNext = () => {
    if (validateStep(activeStep)) {
      setActiveStep((prevStep) => prevStep + 1);
    }
  };

  const handleBack = () => {
    setActiveStep((prevStep) => prevStep - 1);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateStep(activeStep)) {
      return;
    }

    setIsLoading(true);
    setRegistrationStatus(null);
    setErrorMessage('');

    try {
      await new Promise(resolve => setTimeout(resolve, 2000));
      
      // Simulate different scenarios
      const random = Math.random();
      if (random < 0.1) {
        throw new Error('Email already exists');
      } else if (random < 0.15) {
        throw new Error('License number already registered');
      } else if (random < 0.2) {
        throw new Error('Network error. Please try again.');
      }

      setRegistrationStatus('success');
      setTimeout(() => {
        onRegistrationSuccess();
      }, 2000);

    } catch (error) {
      setRegistrationStatus('error');
      setErrorMessage(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  const renderStepContent = (step) => {
    switch (step) {
      case 0:
        return (
          <Fade in={true} timeout={500}>
            <Grid container spacing={3}>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="First Name"
                  name="firstName"
                  value={formData.firstName}
                  onChange={handleInputChange}
                  error={!!errors.firstName}
                  helperText={errors.firstName}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <Person color="action" />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Last Name"
                  name="lastName"
                  value={formData.lastName}
                  onChange={handleInputChange}
                  error={!!errors.lastName}
                  helperText={errors.lastName}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Email Address"
                  name="email"
                  type="email"
                  value={formData.email}
                  onChange={handleInputChange}
                  error={!!errors.email}
                  helperText={errors.email}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <Email color="action" />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Phone Number"
                  name="phone"
                  value={formData.phone}
                  onChange={handleInputChange}
                  error={!!errors.phone}
                  helperText={errors.phone}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <Phone color="action" />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
            </Grid>
          </Fade>
        );

      case 1:
        return (
          <Fade in={true} timeout={500}>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <FormControl fullWidth error={!!errors.specialization}>
                  <InputLabel>Specialization</InputLabel>
                  <Select
                    name="specialization"
                    value={formData.specialization}
                    onChange={handleInputChange}
                    label="Specialization"
                    startAdornment={
                      <InputAdornment position="start">
                        <School color="action" />
                      </InputAdornment>
                    }
                  >
                    {specializations.map((spec) => (
                      <MenuItem key={spec} value={spec}>{spec}</MenuItem>
                    ))}
                  </Select>
                  {errors.specialization && (
                    <Typography variant="caption" color="error" sx={{ mt: 1, display: 'block' }}>
                      {errors.specialization}
                    </Typography>
                  )}
                </FormControl>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Medical License Number"
                  name="licenseNumber"
                  value={formData.licenseNumber}
                  onChange={handleInputChange}
                  error={!!errors.licenseNumber}
                  helperText={errors.licenseNumber}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <Work color="action" />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Years of Experience"
                  name="yearsExperience"
                  type="number"
                  value={formData.yearsExperience}
                  onChange={handleInputChange}
                  error={!!errors.yearsExperience}
                  helperText={errors.yearsExperience}
                  inputProps={{ min: 0, max: 50 }}
                />
              </Grid>
            </Grid>
          </Fade>
        );

      case 2:
        return (
          <Fade in={true} timeout={500}>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Street Address"
                  name="streetAddress"
                  value={formData.streetAddress}
                  onChange={handleInputChange}
                  error={!!errors.streetAddress}
                  helperText={errors.streetAddress}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <Home color="action" />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="City"
                  name="city"
                  value={formData.city}
                  onChange={handleInputChange}
                  error={!!errors.city}
                  helperText={errors.city}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="State/Province"
                  name="state"
                  value={formData.state}
                  onChange={handleInputChange}
                  error={!!errors.state}
                  helperText={errors.state}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="ZIP/Postal Code"
                  name="zipCode"
                  value={formData.zipCode}
                  onChange={handleInputChange}
                  error={!!errors.zipCode}
                  helperText={errors.zipCode}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <LocationOn color="action" />
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
            </Grid>
          </Fade>
        );

      case 3:
        return (
          <Fade in={true} timeout={500}>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Password"
                  name="password"
                  type={showPassword ? 'text' : 'password'}
                  value={formData.password}
                  onChange={handleInputChange}
                  error={!!errors.password}
                  helperText={errors.password}
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
                        >
                          {showPassword ? <VisibilityOff /> : <Visibility />}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Confirm Password"
                  name="confirmPassword"
                  type={showConfirmPassword ? 'text' : 'password'}
                  value={formData.confirmPassword}
                  onChange={handleInputChange}
                  error={!!errors.confirmPassword}
                  helperText={errors.confirmPassword}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <Lock color="action" />
                      </InputAdornment>
                    ),
                    endAdornment: (
                      <InputAdornment position="end">
                        <IconButton
                          onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                          edge="end"
                        >
                          {showConfirmPassword ? <VisibilityOff /> : <Visibility />}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
            </Grid>
          </Fade>
        );

      default:
        return null;
    }
  };

  return (
    <Slide direction="up" in={true} timeout={800}>
      <Paper elevation={24} sx={{
        borderRadius: 3,
        padding: { xs: 3, sm: 4 },
        background: 'rgba(255, 255, 255, 0.98)',
        backdropFilter: 'blur(20px)',
        border: '1px solid rgba(255, 255, 255, 0.3)',
        maxWidth: 800,
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
              Provider Registration
            </Typography>
          </Fade>
          <Fade in={true} timeout={1600}>
            <Typography variant="body1" color="text.secondary">
              Join our healthcare network and start managing your practice
            </Typography>
          </Fade>
        </Box>

        {/* Stepper */}
        <Fade in={true} timeout={1800}>
          <Box sx={{ mb: 4 }}>
            <Stepper activeStep={activeStep} alternativeLabel>
              {steps.map((label) => (
                <Step key={label}>
                  <StepLabel>{label}</StepLabel>
                </Step>
              ))}
            </Stepper>
          </Box>
        </Fade>

        {/* Status Messages */}
        {registrationStatus === 'success' && (
          <Fade in={true}>
            <Alert 
              icon={<CheckCircleOutline />} 
              severity="success" 
              sx={{ mb: 3, borderRadius: 2 }}
            >
              Registration successful! Redirecting to login...
            </Alert>
          </Fade>
        )}

        {registrationStatus === 'error' && (
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

        {/* Form Content */}
        <Fade in={true} timeout={2000}>
          <Box component="form" onSubmit={handleSubmit}>
            {renderStepContent(activeStep)}
            
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 4 }}>
              <Button
                disabled={activeStep === 0}
                onClick={handleBack}
                sx={{ mr: 1 }}
              >
                Back
              </Button>
              
              <Box>
                {activeStep === steps.length - 1 ? (
                  <Button
                    type="submit"
                    variant="contained"
                    disabled={isLoading}
                    sx={{
                      background: 'linear-gradient(45deg, #2e7d32, #4caf50)',
                      transition: 'all 0.3s ease',
                      '&:hover': {
                        background: 'linear-gradient(45deg, #1b5e20, #2e7d32)',
                        transform: 'translateY(-2px)',
                      },
                    }}
                  >
                    {isLoading ? (
                      <CircularProgress size={24} color="inherit" />
                    ) : (
                      'Complete Registration'
                    )}
                  </Button>
                ) : (
                  <Button
                    variant="contained"
                    onClick={handleNext}
                    sx={{
                      background: 'linear-gradient(45deg, #2e7d32, #4caf50)',
                      transition: 'all 0.3s ease',
                      '&:hover': {
                        background: 'linear-gradient(45deg, #1b5e20, #2e7d32)',
                        transform: 'translateY(-2px)',
                      },
                    }}
                  >
                    Next
                  </Button>
                )}
              </Box>
            </Box>
          </Box>
        </Fade>

        {/* Footer */}
        <Fade in={true} timeout={2200}>
          <Box sx={{ textAlign: 'center', mt: 4 }}>
            <Typography variant="body2" color="text.secondary">
              Already have an account?{' '}
              <Button
                onClick={onBackToLogin}
                sx={{
                  color: 'primary.main',
                  textDecoration: 'none',
                  fontWeight: 500,
                  '&:hover': {
                    textDecoration: 'underline',
                  },
                }}
              >
                Sign in here
              </Button>
            </Typography>
          </Box>
        </Fade>
      </Paper>
    </Slide>
  );
};

export default RegistrationForm; 
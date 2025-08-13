import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  FormControl,
  FormControlLabel,
  Radio,
  RadioGroup,
  Box,
  Typography,
  IconButton,
  Grid,
  MenuItem,
  InputAdornment
} from '@mui/material';
import {
  Close,
  Search,
  CalendarToday,
  AccessTime,
  Person,
  VideoCall,
  Home,
  Person as PersonIcon,
  VideoCall as VideoCallIcon,
  Home as HomeIcon
} from '@mui/icons-material';

const ScheduleAppointmentModal = ({ open, onClose, onSave }) => {
  const [formData, setFormData] = useState({
    patientName: '',
    appointmentMode: 'In-Person',
    provider: '',
    appointmentType: '',
    estimatedAmount: '',
    dateTime: '',
    reasonForVisit: ''
  });

  const handleInputChange = (field, value) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSave = () => {
    onSave(formData);
    onClose();
    // Reset form
    setFormData({
      patientName: '',
      appointmentMode: 'In-Person',
      provider: '',
      appointmentType: '',
      estimatedAmount: '',
      dateTime: '',
      reasonForVisit: ''
    });
  };

  const handleClose = () => {
    onClose();
    // Reset form
    setFormData({
      patientName: '',
      appointmentMode: 'In-Person',
      provider: '',
      appointmentType: '',
      estimatedAmount: '',
      dateTime: '',
      reasonForVisit: ''
    });
  };

  // Mock data for dropdowns
  const providers = [
    'Dr. Jacob Jones',
    'Dr. Bessie Cooper',
    'Dr. Sarah Wilson',
    'Dr. Michael Brown'
  ];

  const appointmentTypes = [
    'New Patient',
    'Follow Up',
    'Consultation',
    'Physical Exam',
    'Emergency',
    'Lab Work',
    'Imaging'
  ];

  return (
    <Dialog 
      open={open} 
      onClose={handleClose}
      maxWidth="md"
      fullWidth
      PaperProps={{
        sx: {
          borderRadius: 3,
          boxShadow: '0 20px 60px rgba(0,0,0,0.15)'
        }
      }}
    >
      <DialogTitle sx={{ 
        pb: 1,
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
      }}>
        <Typography variant="h5" sx={{ fontWeight: 600, color: '#1e293b' }}>
          Schedule New Appointment
        </Typography>
        <IconButton
          onClick={handleClose}
          sx={{
            color: '#64748b',
            '&:hover': { backgroundColor: '#f1f5f9' }
          }}
        >
          <Close />
        </IconButton>
      </DialogTitle>

      <DialogContent sx={{ pt: 2 }}>
        <Grid container spacing={3}>
          {/* Patient Name */}
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Patient Name"
              placeholder="Search & Select Patient"
              value={formData.patientName}
              onChange={(e) => handleInputChange('patientName', e.target.value)}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <Search sx={{ color: '#64748b' }} />
                  </InputAdornment>
                ),
              }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  borderRadius: 2,
                  '&:hover fieldset': {
                    borderColor: '#1e3a8a',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#1e3a8a',
                  },
                },
              }}
            />
          </Grid>

          {/* Appointment Mode */}
          <Grid item xs={12} md={6}>
            <FormControl component="fieldset">
              <Typography variant="body2" sx={{ mb: 1, fontWeight: 500, color: '#374151' }}>
                Appointment Mode
              </Typography>
              <RadioGroup
                row
                value={formData.appointmentMode}
                onChange={(e) => handleInputChange('appointmentMode', e.target.value)}
              >
                <FormControlLabel
  value="In-Person"
  control={<Radio sx={{ color: '#1e3a8a', '&.Mui-checked': { color: '#1e3a8a' } }} />}
  label={
    <Box sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
      <PersonIcon sx={{ fontSize: 16, color: '#64748b' }} />
      <Typography variant="body2">In-Person</Typography>
    </Box>
  }
/>
<FormControlLabel
  value="Video Call"
  control={<Radio sx={{ color: '#1e3a8a', '&.Mui-checked': { color: '#1e3a8a' } }} />}
  label={
    <Box sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
      <VideoCallIcon sx={{ fontSize: 16, color: '#64748b' }} />
      <Typography variant="body2">Video Call</Typography>
    </Box>
  }
/>
<FormControlLabel
  value="Home"
  control={<Radio sx={{ color: '#1e3a8a', '&.Mui-checked': { color: '#1e3a8a' } }} />}
  label={
    <Box sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
      <HomeIcon sx={{ fontSize: 16, color: '#64748b' }} />
      <Typography variant="body2">Home</Typography>
    </Box>
  }
/>
              </RadioGroup>
            </FormControl>
          </Grid>

          {/* Provider */}
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Provider"
              placeholder="Search Provider"
              value={formData.provider}
              onChange={(e) => handleInputChange('provider', e.target.value)}
              select
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <Search sx={{ color: '#64748b' }} />
                  </InputAdornment>
                ),
              }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  borderRadius: 2,
                  '&:hover fieldset': {
                    borderColor: '#1e3a8a',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#1e3a8a',
                  },
                },
              }}
            >
              {providers.map((provider) => (
                <MenuItem key={provider} value={provider}>
                  {provider}
                </MenuItem>
              ))}
            </TextField>
          </Grid>

          {/* Appointment Type */}
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Appointment Type"
              placeholder="Select Type"
              value={formData.appointmentType}
              onChange={(e) => handleInputChange('appointmentType', e.target.value)}
              select
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <Search sx={{ color: '#64748b' }} />
                  </InputAdornment>
                ),
              }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  borderRadius: 2,
                  '&:hover fieldset': {
                    borderColor: '#1e3a8a',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#1e3a8a',
                  },
                },
              }}
            >
              {appointmentTypes.map((type) => (
                <MenuItem key={type} value={type}>
                  {type}
                </MenuItem>
              ))}
            </TextField>
          </Grid>

          {/* Estimated Amount */}
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Estimated Amount ($)"
              placeholder="Enter Amount"
              value={formData.estimatedAmount}
              onChange={(e) => handleInputChange('estimatedAmount', e.target.value)}
              type="number"
              InputProps={{
                startAdornment: <InputAdornment position="start">$</InputAdornment>,
              }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  borderRadius: 2,
                  '&:hover fieldset': {
                    borderColor: '#1e3a8a',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#1e3a8a',
                  },
                },
              }}
            />
          </Grid>

          {/* Date & Time */}
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Date & Time"
              placeholder="Choose Date"
              value={formData.dateTime}
              onChange={(e) => handleInputChange('dateTime', e.target.value)}
              type="datetime-local"
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <CalendarToday sx={{ color: '#64748b' }} />
                  </InputAdornment>
                ),
              }}
              InputLabelProps={{
                shrink: true,
              }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  borderRadius: 2,
                  '&:hover fieldset': {
                    borderColor: '#1e3a8a',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#1e3a8a',
                  },
                },
              }}
            />
          </Grid>

          {/* Reason for Visit */}
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Reason for Visit"
              placeholder="Enter Reason"
              value={formData.reasonForVisit}
              onChange={(e) => handleInputChange('reasonForVisit', e.target.value)}
              multiline
              rows={3}
              sx={{
                '& .MuiOutlinedInput-root': {
                  borderRadius: 2,
                  '&:hover fieldset': {
                    borderColor: '#1e3a8a',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#1e3a8a',
                  },
                },
              }}
            />
          </Grid>
        </Grid>
      </DialogContent>

      <DialogActions sx={{ px: 3, pb: 3 }}>
        <Button
          onClick={handleClose}
          variant="outlined"
          sx={{
            borderColor: '#d1d5db',
            color: '#374151',
            '&:hover': {
              borderColor: '#9ca3af',
              backgroundColor: '#f9fafb',
            }
          }}
        >
          Close
        </Button>
        <Button
          onClick={handleSave}
          variant="contained"
          sx={{
            backgroundColor: '#1e3a8a',
            '&:hover': {
              backgroundColor: '#1e40af',
            }
          }}
        >
          Save & Close
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ScheduleAppointmentModal;

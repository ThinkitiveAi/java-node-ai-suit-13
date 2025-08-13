import React, { useState } from 'react';
import {
  Container,
  Typography,
  Box,
  Button,
  TextField,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Chip,
  Fade
} from '@mui/material';
import {
  Delete,
  Add,
  AccessTime,
  CalendarToday
} from '@mui/icons-material';

const ProviderAvailability = ({ onNavigate }) => {
  const [selectedProvider, setSelectedProvider] = useState('John Doe');
  const [timeZone, setTimeZone] = useState('');
  
  // Day-wise availability state
  const [availability, setAvailability] = useState([
    { day: 'Monday', from: '09:00', till: '18:00', enabled: true },
    { day: 'Tuesday', from: '09:00', till: '18:00', enabled: true },
    { day: 'Wednesday', from: '09:00', till: '18:00', enabled: true },
    { day: 'Thursday', from: '09:00', till: '18:00', enabled: true },
    { day: 'Friday', from: '09:00', till: '18:00', enabled: true },
    { day: 'Saturday', from: '09:00', till: '18:00', enabled: true },
    { day: 'Sunday', from: '09:00', till: '18:00', enabled: false }
  ]);

  // Block days state
  const [blockDays, setBlockDays] = useState([
    { date: '', from: '', till: '' },
    { date: '', from: '', till: '' }
  ]);

  const providers = [
    'John Doe',
    'Dr. Jacob Jones',
    'Dr. Bessie Cooper',
    'Dr. Sarah Wilson'
  ];

  const timeZones = [
    'UTC-8 (Pacific Time)',
    'UTC-7 (Mountain Time)',
    'UTC-6 (Central Time)',
    'UTC-5 (Eastern Time)',
    'UTC+0 (GMT)',
    'UTC+1 (Central European Time)',
    'UTC+5:30 (India Standard Time)'
  ];

  const handleAvailabilityChange = (index, field, value) => {
    const newAvailability = [...availability];
    newAvailability[index][field] = value;
    setAvailability(newAvailability);
  };

  const handleDeleteAvailability = (index) => {
    const newAvailability = availability.filter((_, i) => i !== index);
    setAvailability(newAvailability);
  };

  const handleBlockDayChange = (index, field, value) => {
    const newBlockDays = [...blockDays];
    newBlockDays[index][field] = value;
    setBlockDays(newBlockDays);
  };

  const handleAddBlockDay = () => {
    setBlockDays([...blockDays, { date: '', from: '', till: '' }]);
  };

  const handleDeleteBlockDay = (index) => {
    const newBlockDays = blockDays.filter((_, i) => i !== index);
    setBlockDays(newBlockDays);
  };

  const handleSave = () => {
    console.log('Saving availability settings:', {
      provider: selectedProvider,
      timeZone,
      availability,
      blockDays
    });
    // Handle save logic
  };

  const handleClose = () => {
    onNavigate('dashboard');
  };

  return (
    <Box sx={{ flexGrow: 1, background: '#f8fafc', minHeight: '100vh' }}>
      <Container maxWidth="xl" sx={{ py: 4, px: { xs: 2, md: 4 } }}>
        <Fade in={true} timeout={800}>
          <Box>
            {/* Header Section */}
            <Box sx={{ mb: 4 }}>
              <Typography variant="h4" component="h1" gutterBottom sx={{ 
                fontWeight: 700,
                color: '#1e293b',
                mb: 1
              }}>
                Settings
              </Typography>
              <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
                Manage provider availability and scheduling settings
              </Typography>
            </Box>

            <Grid container spacing={4}>
              {/* Left Section - Day Wise Availability */}
              <Grid item xs={12} md={6}>
                <Paper 
                  elevation={0}
                  sx={{ 
                    p: 3,
                    borderRadius: 3,
                    border: '1px solid #e2e8f0',
                    height: 'fit-content'
                  }}
                >
                  <Typography variant="h6" sx={{ fontWeight: 600, mb: 3, color: '#1e293b' }}>
                    Day Wise Availability
                  </Typography>

                  {/* Provider Selection */}
                  <FormControl fullWidth sx={{ mb: 3 }}>
                    <InputLabel>Provider</InputLabel>
                    <Select
                      value={selectedProvider}
                      onChange={(e) => setSelectedProvider(e.target.value)}
                      label="Provider"
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
                    </Select>
                  </FormControl>

                  {/* Availability Table */}
                  <TableContainer>
                    <Table size="small">
                      <TableHead>
                        <TableRow sx={{ backgroundColor: '#f8fafc' }}>
                          <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Day</TableCell>
                          <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>From</TableCell>
                          <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Till</TableCell>
                          <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Action</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {availability.map((day, index) => (
                          <TableRow key={day.day}>
                            <TableCell>
                              <FormControl fullWidth size="small">
                                <Select
                                  value={day.day}
                                  onChange={(e) => handleAvailabilityChange(index, 'day', e.target.value)}
                                  sx={{
                                    '& .MuiOutlinedInput-root': {
                                      borderRadius: 1,
                                    },
                                  }}
                                >
                                  <MenuItem value="Monday">Monday</MenuItem>
                                  <MenuItem value="Tuesday">Tuesday</MenuItem>
                                  <MenuItem value="Wednesday">Wednesday</MenuItem>
                                  <MenuItem value="Thursday">Thursday</MenuItem>
                                  <MenuItem value="Friday">Friday</MenuItem>
                                  <MenuItem value="Saturday">Saturday</MenuItem>
                                  <MenuItem value="Sunday">Sunday</MenuItem>
                                </Select>
                              </FormControl>
                            </TableCell>
                            <TableCell>
                              <TextField
                                type="time"
                                value={day.from}
                                onChange={(e) => handleAvailabilityChange(index, 'from', e.target.value)}
                                size="small"
                                InputProps={{
                                  startAdornment: (
                                    <AccessTime sx={{ fontSize: 16, color: '#64748b', mr: 1 }} />
                                  ),
                                }}
                                sx={{
                                  '& .MuiOutlinedInput-root': {
                                    borderRadius: 1,
                                  },
                                }}
                              />
                            </TableCell>
                            <TableCell>
                              <TextField
                                type="time"
                                value={day.till}
                                onChange={(e) => handleAvailabilityChange(index, 'till', e.target.value)}
                                size="small"
                                InputProps={{
                                  startAdornment: (
                                    <AccessTime sx={{ fontSize: 16, color: '#64748b', mr: 1 }} />
                                  ),
                                }}
                                sx={{
                                  '& .MuiOutlinedInput-root': {
                                    borderRadius: 1,
                                  },
                                }}
                              />
                            </TableCell>
                            <TableCell>
                              <IconButton
                                size="small"
                                onClick={() => handleDeleteAvailability(index)}
                                sx={{
                                  color: '#ef4444',
                                  '&:hover': {
                                    backgroundColor: '#fef2f2',
                                  }
                                }}
                              >
                                <Delete sx={{ fontSize: 18 }} />
                              </IconButton>
                            </TableCell>
                          </TableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </TableContainer>
                </Paper>
              </Grid>

              {/* Right Section - Slot Creation & Block Days */}
              <Grid item xs={12} md={6}>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
                  {/* Slot Creation Setting */}
                  <Paper 
                    elevation={0}
                    sx={{ 
                      p: 3,
                      borderRadius: 3,
                      border: '1px solid #e2e8f0'
                    }}
                  >
                    <Typography variant="h6" sx={{ fontWeight: 600, mb: 3, color: '#1e293b' }}>
                      Slot Creation Setting
                    </Typography>

                    <FormControl fullWidth>
                      <InputLabel>Time Zone</InputLabel>
                      <Select
                        value={timeZone}
                        onChange={(e) => setTimeZone(e.target.value)}
                        label="Time Zone"
                        placeholder="Select Time Zone"
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
                        {timeZones.map((tz) => (
                          <MenuItem key={tz} value={tz}>
                            {tz}
                          </MenuItem>
                        ))}
                      </Select>
                    </FormControl>
                  </Paper>

                  {/* Block Days */}
                  <Paper 
                    elevation={0}
                    sx={{ 
                      p: 3,
                      borderRadius: 3,
                      border: '1px solid #e2e8f0'
                    }}
                  >
                    <Typography variant="h6" sx={{ fontWeight: 600, mb: 3, color: '#1e293b' }}>
                      Block Days
                    </Typography>

                    {blockDays.map((blockDay, index) => (
                      <Box key={index} sx={{ mb: 2, p: 2, backgroundColor: '#f8fafc', borderRadius: 2 }}>
                        <Grid container spacing={2} alignItems="center">
                          <Grid item xs={12} md={4}>
                            <TextField
                              fullWidth
                              type="date"
                              label="Date"
                              placeholder="Select Date"
                              value={blockDay.date}
                              onChange={(e) => handleBlockDayChange(index, 'date', e.target.value)}
                              size="small"
                              InputProps={{
                                startAdornment: (
                                  <CalendarToday sx={{ fontSize: 16, color: '#64748b', mr: 1 }} />
                                ),
                              }}
                              InputLabelProps={{
                                shrink: true,
                              }}
                              sx={{
                                '& .MuiOutlinedInput-root': {
                                  borderRadius: 1,
                                },
                              }}
                            />
                          </Grid>
                          <Grid item xs={12} md={3}>
                            <TextField
                              fullWidth
                              type="time"
                              label="From"
                              placeholder="Select Start Time"
                              value={blockDay.from}
                              onChange={(e) => handleBlockDayChange(index, 'from', e.target.value)}
                              size="small"
                              InputProps={{
                                startAdornment: (
                                  <AccessTime sx={{ fontSize: 16, color: '#64748b', mr: 1 }} />
                                ),
                              }}
                              sx={{
                                '& .MuiOutlinedInput-root': {
                                  borderRadius: 1,
                                },
                              }}
                            />
                          </Grid>
                          <Grid item xs={12} md={3}>
                            <TextField
                              fullWidth
                              type="time"
                              label="Till"
                              placeholder="Select End Time"
                              value={blockDay.till}
                              onChange={(e) => handleBlockDayChange(index, 'till', e.target.value)}
                              size="small"
                              InputProps={{
                                startAdornment: (
                                  <AccessTime sx={{ fontSize: 16, color: '#64748b', mr: 1 }} />
                                ),
                              }}
                              sx={{
                                '& .MuiOutlinedInput-root': {
                                  borderRadius: 1,
                                },
                              }}
                            />
                          </Grid>
                          <Grid item xs={12} md={2}>
                            <IconButton
                              size="small"
                              onClick={() => handleDeleteBlockDay(index)}
                              sx={{
                                color: '#ef4444',
                                '&:hover': {
                                  backgroundColor: '#fef2f2',
                                }
                              }}
                            >
                              <Delete sx={{ fontSize: 18 }} />
                            </IconButton>
                          </Grid>
                        </Grid>
                      </Box>
                    ))}

                    <Button
                      variant="outlined"
                      startIcon={<Add />}
                      onClick={handleAddBlockDay}
                      sx={{
                        borderColor: '#1e3a8a',
                        color: '#1e3a8a',
                        '&:hover': {
                          borderColor: '#1e40af',
                          backgroundColor: '#eff6ff',
                        }
                      }}
                    >
                      + Add Block Days
                    </Button>
                  </Paper>
                </Box>
              </Grid>
            </Grid>

            {/* Action Buttons */}
            <Box sx={{ display: 'flex', justifyContent: 'flex-end', gap: 2, mt: 4 }}>
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
                Save
              </Button>
            </Box>
          </Box>
        </Fade>
      </Container>
    </Box>
  );
};

export default ProviderAvailability;

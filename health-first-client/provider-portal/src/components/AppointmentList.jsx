import React, { useState } from 'react';
import {
  Container,
  Typography,
  Box,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Chip,
  IconButton,
  Pagination,
  Stack,
  Fade
} from '@mui/material';
import {
  Add,
  Description,
  Edit,
  Schedule
} from '@mui/icons-material';

const AppointmentList = ({ onScheduleAppointment }) => {
  const [page, setPage] = useState(1);

  // Mock appointment data based on the screenshot
  const appointments = [
    {
      id: 1,
      dateTime: '02/24/21, 11:17am',
      type: 'New',
      patientName: 'Heena West (F)',
      dateOfBirth: '10-21-1959 (65)',
      contactDetails: '202-555-0188',
      providerName: 'Jacob Jones',
      reasonForVisit: 'Infection Disease',
      status: 'Scheduled'
    },
    {
      id: 2,
      dateTime: '02/24/21, 10:30am',
      type: 'Follow Up',
      patientName: 'Arlene McCoy (M)',
      dateOfBirth: '05-15-1982 (42)',
      contactDetails: '202-555-0123',
      providerName: 'Bessie Cooper',
      reasonForVisit: 'Itching',
      status: 'Checked In'
    },
    {
      id: 3,
      dateTime: '02/24/21, 09:45am',
      type: 'New',
      patientName: 'Esther Howard (F)',
      dateOfBirth: '12-03-1975 (49)',
      contactDetails: '202-555-0456',
      providerName: 'Jacob Jones',
      reasonForVisit: 'Blurred Vision',
      status: 'Checked In'
    },
    {
      id: 4,
      dateTime: '02/24/21, 08:30am',
      type: 'Follow Up',
      patientName: 'Darrell Steward (M)',
      dateOfBirth: '03-18-1990 (34)',
      contactDetails: '202-555-0789',
      providerName: 'Bessie Cooper',
      reasonForVisit: 'Headache',
      status: 'Scheduled'
    },
    {
      id: 5,
      dateTime: '02/24/21, 02:15pm',
      type: 'New',
      patientName: 'Jane Cooper (F)',
      dateOfBirth: '07-22-1988 (36)',
      contactDetails: '202-555-0321',
      providerName: 'Jacob Jones',
      reasonForVisit: 'Fever',
      status: 'Cancelled'
    },
    {
      id: 6,
      dateTime: '02/24/21, 01:00pm',
      type: 'Follow Up',
      patientName: 'Esther Howard (F)',
      dateOfBirth: '12-03-1975 (49)',
      contactDetails: '202-555-0456',
      providerName: 'Bessie Cooper',
      reasonForVisit: 'Follow-up Check',
      status: 'Cancelled'
    },
    {
      id: 7,
      dateTime: '02/24/21, 11:45am',
      type: 'New',
      patientName: 'Bessie Cooper (F)',
      dateOfBirth: '09-14-1965 (59)',
      contactDetails: '202-555-0654',
      providerName: 'Jacob Jones',
      reasonForVisit: 'Chest Pain',
      status: 'Cancelled'
    },
    {
      id: 8,
      dateTime: '02/24/21, 10:15am',
      type: 'Follow Up',
      patientName: 'Bessie Cooper (F)',
      dateOfBirth: '09-14-1965 (59)',
      contactDetails: '202-555-0654',
      providerName: 'Jacob Jones',
      reasonForVisit: 'Blood Pressure Check',
      status: 'In Exam'
    },
    {
      id: 9,
      dateTime: '02/24/21, 09:30am',
      type: 'New',
      patientName: 'Arlene McCoy (M)',
      dateOfBirth: '05-15-1982 (42)',
      contactDetails: '202-555-0123',
      providerName: 'Bessie Cooper',
      reasonForVisit: 'Annual Physical',
      status: 'In Exam'
    },
    {
      id: 10,
      dateTime: '02/24/21, 08:00am',
      type: 'Follow Up',
      patientName: 'Darrell Steward (M)',
      dateOfBirth: '03-18-1990 (34)',
      contactDetails: '202-555-0789',
      providerName: 'Jacob Jones',
      reasonForVisit: 'Lab Results Review',
      status: 'Scheduled'
    },
    {
      id: 11,
      dateTime: '02/24/21, 03:30pm',
      type: 'New',
      patientName: 'Jane Cooper (F)',
      dateOfBirth: '07-22-1988 (36)',
      contactDetails: '202-555-0321',
      providerName: 'Bessie Cooper',
      reasonForVisit: 'Allergy Consultation',
      status: 'Scheduled'
    }
  ];

  const getStatusColor = (status) => {
    switch (status) {
      case 'Scheduled':
        return { backgroundColor: '#1976d2', color: 'white' };
      case 'Checked In':
        return { backgroundColor: '#e91e63', color: 'white' };
      case 'Cancelled':
        return { backgroundColor: '#f44336', color: 'white' };
      case 'In Exam':
        return { backgroundColor: '#9c27b0', color: 'white' };
      default:
        return { backgroundColor: '#757575', color: 'white' };
    }
  };

  const handlePageChange = (event, newPage) => {
    setPage(newPage);
  };

  const handleScheduleAppointment = () => {
    if (onScheduleAppointment) {
      onScheduleAppointment();
    }
  };

  const handleStartAppointment = (appointment) => {
    console.log('Start appointment:', appointment);
    // Handle start appointment logic
  };

  const handleEditAppointment = (appointment) => {
    console.log('Edit appointment:', appointment);
    // Handle edit appointment logic
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
                Appointment List
              </Typography>
              <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
                Manage and view all scheduled appointments
              </Typography>
            </Box>

            {/* Action Button */}
            <Box sx={{ display: 'flex', justifyContent: 'flex-end', mb: 3 }}>
              <Button
                variant="contained"
                startIcon={<Add />}
                onClick={handleScheduleAppointment}
                sx={{
                  px: 3,
                  py: 1.5,
                  fontSize: '0.9rem',
                  fontWeight: 600,
                  backgroundColor: '#1e3a8a',
                  '&:hover': {
                    backgroundColor: '#1e40af',
                  }
                }}
              >
                + Schedule Appointment
              </Button>
            </Box>

            {/* Appointments Table */}
            <Paper 
              elevation={0}
              sx={{ 
                borderRadius: 3,
                border: '1px solid #e2e8f0',
                overflow: 'hidden'
              }}
            >
              <TableContainer>
                <Table sx={{ minWidth: 650 }}>
                  <TableHead>
                    <TableRow sx={{ backgroundColor: '#f8fafc' }}>
                      <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Date & Time</TableCell>
                      <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Appointment Type</TableCell>
                      <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Patient Name</TableCell>
                      <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Date of Birth</TableCell>
                      <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Contact Details</TableCell>
                      <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Provider Name</TableCell>
                      <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Reason for Visit</TableCell>
                      <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Status</TableCell>
                      <TableCell sx={{ fontWeight: 600, color: '#64748b' }}>Action</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {appointments.map((appointment) => (
                      <TableRow 
                        key={appointment.id}
                        sx={{ 
                          '&:hover': { backgroundColor: '#f8fafc' },
                          '&:last-child td, &:last-child th': { border: 0 }
                        }}
                      >
                        <TableCell sx={{ color: '#1e293b', fontWeight: 500 }}>
                          {appointment.dateTime}
                        </TableCell>
                        <TableCell>
                          <Chip 
                            label={appointment.type} 
                            size="small"
                            sx={{ 
                              backgroundColor: appointment.type === 'New' ? '#e3f2fd' : '#f3e5f5',
                              color: appointment.type === 'New' ? '#1976d2' : '#9c27b0',
                              fontWeight: 500
                            }}
                          />
                        </TableCell>
                        <TableCell sx={{ color: '#1e293b', fontWeight: 500 }}>
                          {appointment.patientName}
                        </TableCell>
                        <TableCell sx={{ color: '#64748b' }}>
                          {appointment.dateOfBirth}
                        </TableCell>
                        <TableCell sx={{ color: '#64748b' }}>
                          {appointment.contactDetails}
                        </TableCell>
                        <TableCell sx={{ color: '#1e293b', fontWeight: 500 }}>
                          {appointment.providerName}
                        </TableCell>
                        <TableCell sx={{ color: '#64748b' }}>
                          {appointment.reasonForVisit}
                        </TableCell>
                        <TableCell>
                          <Chip 
                            label={appointment.status} 
                            size="small"
                            sx={{
                              ...getStatusColor(appointment.status),
                              fontWeight: 500,
                              fontSize: '0.75rem'
                            }}
                          />
                        </TableCell>
                        <TableCell>
                          <Box sx={{ display: 'flex', gap: 1 }}>
                            <IconButton
                              size="small"
                              onClick={() => handleStartAppointment(appointment)}
                              sx={{
                                backgroundColor: '#e3f2fd',
                                color: '#1976d2',
                                '&:hover': {
                                  backgroundColor: '#bbdefb',
                                }
                              }}
                            >
                              <Description sx={{ fontSize: 16 }} />
                            </IconButton>
                            <IconButton
                              size="small"
                              onClick={() => handleEditAppointment(appointment)}
                              sx={{
                                backgroundColor: '#fff3e0',
                                color: '#f57c00',
                                '&:hover': {
                                  backgroundColor: '#ffe0b2',
                                }
                              }}
                            >
                              <Edit sx={{ fontSize: 16 }} />
                            </IconButton>
                          </Box>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </Paper>

            {/* Pagination */}
            <Box sx={{ 
              display: 'flex', 
              justifyContent: 'space-between', 
              alignItems: 'center', 
              mt: 3 
            }}>
              <Typography variant="body2" color="text.secondary">
                Showing 1 to 11 of 100 entries
              </Typography>
              <Stack spacing={2}>
                <Pagination 
                  count={5} 
                  page={page} 
                  onChange={handlePageChange}
                  shape="rounded"
                  size="small"
                  sx={{
                    '& .MuiPaginationItem-root': {
                      color: '#64748b',
                      '&.Mui-selected': {
                        backgroundColor: '#1e3a8a',
                        color: 'white',
                        '&:hover': {
                          backgroundColor: '#1e40af',
                        }
                      }
                    }
                  }}
                />
              </Stack>
            </Box>
          </Box>
        </Fade>
      </Container>
    </Box>
  );
};

export default AppointmentList;

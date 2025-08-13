import React, { useState } from 'react';
import { Routes, Route, Navigate, useNavigate, useLocation } from 'react-router-dom';
import { Box } from '@mui/material';
import Navigation from './Navigation';
import Dashboard from './Dashboard';
import AppointmentList from './AppointmentList';
import ProviderAvailability from './ProviderAvailability';
import ScheduleAppointmentModal from './ScheduleAppointmentModal';

const MainLayout = ({ isGuestMode = false, onLogout }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const [showScheduleModal, setShowScheduleModal] = useState(false);

  // Get current page from location
  const getCurrentPage = () => {
    const path = location.pathname;
    if (path === '/' || path === '/dashboard') return 'dashboard';
    if (path === '/scheduling') return 'scheduling';
    if (path === '/settings') return 'settings';
    return 'dashboard';
  };

  const handleNavigate = (page) => {
    switch (page) {
      case 'dashboard':
        navigate('/');
        break;
      case 'scheduling':
        navigate('/scheduling');
        break;
      case 'settings':
        navigate('/settings');
        break;
      default:
        navigate('/');
    }
  };

  const handleCloseScheduleModal = () => {
    setShowScheduleModal(false);
  };

  const handleSaveAppointment = (appointmentData) => {
    console.log('Saving appointment:', appointmentData);
    // Handle appointment saving logic
    setShowScheduleModal(false);
  };

  const handleOpenScheduleModal = () => {
    setShowScheduleModal(true);
  };

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Navigation 
        currentPage={getCurrentPage()}
        onNavigate={handleNavigate}
        onLogout={onLogout}
        isGuestMode={isGuestMode}
        userName="John Doe"
      />
      
      <Box sx={{ flexGrow: 1 }}>
        <Routes>
          <Route path="/" element={<Dashboard onLogout={onLogout} isGuestMode={isGuestMode} />} />
          <Route path="/dashboard" element={<Dashboard onLogout={onLogout} isGuestMode={isGuestMode} />} />
          <Route path="/scheduling" element={<AppointmentList onScheduleAppointment={handleOpenScheduleModal} />} />
          <Route path="/settings" element={<ProviderAvailability onNavigate={handleNavigate} />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </Box>

      {/* Schedule Appointment Modal */}
      <ScheduleAppointmentModal
        open={showScheduleModal}
        onClose={handleCloseScheduleModal}
        onSave={handleSaveAppointment}
      />
    </Box>
  );
};

export default MainLayout;

// Create a theme instance.
import { createTheme, responsiveFontSizes } from '@mui/material';
import { amber } from '@mui/material/colors';

let theme = createTheme({
  palette: {
    mode: 'dark',
    secondary: amber,
    // divider: deepOrange[700],
  },
});

theme = responsiveFontSizes(theme);

export default theme;

import legacy from '@vitejs/plugin-legacy';
import process from 'process';

/** @type {import('vite').UserConfig} */
export default {
    base: './',
    server: {
        host: true,
        port: 8000,
    },
    define: {
        APP_VERSION: JSON.stringify(process.env.npm_package_version),
    },
    plugins: [
        legacy({
            targets: ['defaults', 'not IE 11'],
        }),
    ],
};

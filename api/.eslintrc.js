module.exports = {
    extends: ['prettier', 'eslint:recommended'],
    plugins: ['prettier'],
    parser: 'esprima',
    env: {
        node: true,
        commonjs: true,
    },
    parserOptions: {
        ecmaVersion: 6,
        sourceType: 'module',
    },
    rules: {
        semi: 2,
        quotes: [2, 'single', { avoidEscape: true }],
        'comma-dangle': [2, 'always'],
        'prettier/prettier': 2,
        'brace-style': [2, '1tbs', { allowSingleLine: true }],
    },
};

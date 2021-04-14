FROM node:12-alpine

WORKDIR '/app'

ENV PATH /app/node_modules/.bin:$PATH

COPY package.json ./
COPY package-lock.json ./

RUN npm install --silent && npm install react-scripts@3.4.1 -g --silent && npm install -g serve --silent

COPY . ./

RUN npm run build

EXPOSE 3000

CMD ["serve", "-s", "build", "-l", "3000"]

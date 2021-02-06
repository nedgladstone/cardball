import superagent from 'superagent';
import buildUrl from 'build-url';

// constants

const cardService = 'localhost:8080';
const statService = 'localhost:8081';
const gameService = 'localhost:8082';

const header = `
    <img id="rally-logo" src="img/baseball.png" height="40px" style="float: left; padding: 3px 30px 1px 60px;">
    <div class="header-name-div headerTree" style="font-size: 20pt; padding-top: 5px">CardBall</div>`;
const footer = `
    <div style="float: left; font-size: 8pt; padding: 8px 20px 0px 10px">NG 2021</div>`;

// types

class Game {
    constructor() {
    }
}

// global functions

// utility functions

function expand(obj) {
    return JSON.stringify(obj, null, 1);
}

function displayWebError(command, err) {
    console.error(`Exception performing ${command}:`);
    console.log(err);
}

// page view functions

function showPage(headerHtml, headerHeight, bodyHtml, footerHtml, footerHeight) {
    const header = document.getElementById('header');
    header.innerHTML = headerHtml;
    header.style.height = `${headerHeight}.px`;

    const main = document.getElementById('main');
    main.innerHTML = bodyHtml;
    main.style.height = `${window.innerHeight - headerHeight - footerHeight - 20}px`;

    const footer = document.getElementById('footer');
    footer.innerHTML = footerHtml;
    footer.style.height = `${footerHeight}.px`;
}

function showFieldPage() {
    document.getElementById('the-body').classList.remove('treeable');

    const body = `
        <div id='tree-div' style="overflow-x: scroll">
            <canvas id="treeCanvas"></canvas>
        </div>`;

    showPage(rallyHeader, 40, body, rallyFooter, 20);

    const div = document.getElementById('tree-div');
    div.style.width = '100%';
    div.style.height = '100%'; //`${document.getElementById('main').offsetHeight - 10}px`;

    const canvas = document.getElementById('treeCanvas');
    canvas.style.height='99%';
    canvas.height = canvas.offsetHeight;

    document.getElementById('rally-logo').addEventListener('click', startAgain);
}

function getBoxLeftX(node, treeProps) {
    return Math.round(node.position * treeProps.itemTotalWidth) + 50;
}

function getBoxMiddleX(node, treeProps) {
    return getBoxLeftX(node, treeProps) + Math.round(treeProps.itemWidth / 2);
}

function getBoxTopY(node, treeProps) {
    return 10 + Math.round((node.level - treeProps.topRowLevel) * treeProps.rowTotalHeight);
}

function getBoxBottomY(node, treeProps) {
    return getBoxTopY(node, treeProps) + treeProps.itemHeight;
}

function drawRectangle(ctx, x, y, width, height, cornerRadius, fillStyle, strokeStyle) {
    ctx.fillStyle = fillStyle;
    ctx.strokeStyle = strokeStyle;
    ctx.beginPath();
    ctx.moveTo(x + cornerRadius, y);
    ctx.lineTo(x + width - cornerRadius, y);
    ctx.quadraticCurveTo(x + width, y, x + width, y + cornerRadius);
    ctx.lineTo(x + width, y + height - cornerRadius);
    ctx.quadraticCurveTo(x + width, y + height, x + width - cornerRadius, y + height);
    ctx.lineTo(x + cornerRadius, y + height);
    ctx.quadraticCurveTo(x, y + height, x, y + height - cornerRadius);
    ctx.lineTo(x, y + cornerRadius);
    ctx.quadraticCurveTo(x, y, x + cornerRadius, y);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();
}

function wrapText(ctx, text, width, maxLines) {
    const words = text.split(' ');
    let lines = [];
    let curLine = '';

    for (const word of words) {
        const curWidth = ctx.measureText(curLine + ' ' + word).width;
        if (curWidth < width) {
            curLine += ' ' + word;
        } else {
            if (lines.length + 1 >= maxLines) {
                lines.push(curLine + '...');
                return lines;
            }
            lines.push(curLine);
            curLine = word;
        }
    }
    lines.push(curLine);
    return lines;
}

function stripHtml(text) {
    return text.replace(/<[^>]*>?/gm, '');
}

function drawProgressBar(ctx, percentComplete, x, y, width) {
    ctx.font = '8pt Open Sans';
    ctx.fillStyle = '#333';
    ctx.textAlign = 'right';
    ctx.fillText(`${Math.round(percentComplete * 100)}%`, x + 10, y + 7);

    const textWidth = 15;
    const barWidth = width - textWidth;
    const completeWidth = barWidth * percentComplete;
    const completeColor = (percentComplete < 0.5) ? 'red' : 'green';
    drawRectangle(ctx, x + textWidth, y, barWidth, 10, 3, 'white', 'black');
    drawRectangle(ctx, x + textWidth, y, completeWidth, 10, 3, completeColor, 'rgba(0, 255, 0, 0)');
}


function drawNode(node, treeProps) {
    if (node.isRoot()) {
        return;
    }

    const item = node.portfolioItem;
    const left = getBoxLeftX(node, treeProps);
    const top = getBoxTopY(node, treeProps);
    const width = treeProps.itemWidth;
    const height = treeProps.itemHeight;
    const horizontalPadding = 7;
    const verticalPadding = 10;
    const titleTextGap = 10;

    const ctx = treeProps.canvas.getContext('2d');
    drawRectangle(ctx, left, top, width, height, 5, 'white', node.isTargetNode ? 'blue' : 'white');

    ctx.font='14px FontAwesome';
    ctx.fillStyle = item.DisplayColor;
    ctx.textAlign = 'left';
    ctx.fillText('\uf0b1',left + 10,top + verticalPadding + 15);

    ctx.font = '10pt Open Sans';
    ctx.fillStyle = '#5691f0';
    ctx.textAlign = 'center';
    ctx.fillText(item.FormattedID, left + width / 2, top + verticalPadding + 15);

    if (item.Ready == 'true') {
        ctx.font = '14px FontAwesome';
        ctx.fillStyle = 'green';
        ctx.fillText('\uf058', left + width - 10 - 10, top + verticalPadding + 15);
    } else if (item.Blocked === 'true') {
        ctx.font = '14px FontAwesome';
        ctx.fillStyle = 'red';
        ctx.fillText('\uf75a', left + width + 10 + 10, top + verticalPadding + 15);
    }

    ctx.font = '10pt Open Sans';// Condensed';
    ctx.fillStyle = 'black';//'#333840';
    ctx.textAlign = 'left';

    wrapText(ctx, stripHtml(item.Name), treeProps.itemWidth - horizontalPadding * 2, 5).forEach((line, i) =>
        ctx.fillText(line,
            left + horizontalPadding, top + verticalPadding + 30 + titleTextGap + i * 16));

    const progressBarWidth = width * 0.7 - horizontalPadding * 2;
    drawProgressBar(
        ctx,
        item.PercentDoneByStoryPlanEstimate,
        left + (width - progressBarWidth) / 2,
        top + height - verticalPadding - 10,
        progressBarWidth);

    console.log(`Node: ${item.FormattedID} (${left}, ${top}) [${node.children.length}]`);
}

function drawLine90s(fromX, fromY, toX, toY, treeProps) {
    const ctx = treeProps.canvas.getContext('2d');

    ctx.strokeStyle = 'black';
    ctx.lineWidth = 1;
    ctx.beginPath();
    ctx.moveTo(fromX, fromY);
    ctx.lineTo(fromX, (fromY + toY) / 2);
    ctx.lineTo(toX, (fromY + toY) / 2);
    ctx.lineTo(toX, toY);
    ctx.stroke();
    ctx.closePath();
}

function drawLineStraight(fromX, fromY, toX, toY, treeProps) {
    const ctx = treeProps.canvas.getContext('2d');

    ctx.strokeStyle = 'black';
    ctx.lineWidth = 1;
    ctx.beginPath();
    ctx.moveTo(fromX, fromY);
    ctx.lineTo(toX, toY);
    ctx.stroke();
    ctx.closePath();
}


async function getFromUrl(url, handleError) {
    console.log(`getting from ${url}`);
    try {
        const result = await agent
            .get(url);

        console.log(`Query of ${url} returned:`);
        console.log(result);
        return result;
    } catch (err) {
        handleError('query', err);
    }
}


// main

console.log("CardBall running");

let agent = superagent.agent().set('Accept', 'application/json').set('Content-Type', 'application/json');

startAgain();

console.log(`It's all event-driven from here.`);

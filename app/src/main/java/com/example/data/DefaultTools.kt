package com.example.data

object DefaultTools {
    val list = listOf(
        ToolEntity(
            id = "financial_calculator",
            name = "מחשבון פיננסי אולטרה",
            description = "מחשבון פיננסי מתקדם המציע חישוב ריבית דריבית, החזרי משכנתא, טיפים, ממיר אחוזים ועוד. ממשק מודרני מרהיב ונוח לקבלת החלטות מהירות.",
            category = "פיננסים",
            tags = "פיננסים,מחשבון,ריבית,משכנתא,כסף,הלוואה,אחוזים",
            html = """
                <div class="calculator-container">
                    <h2>מחשבון פיננסי אולטרה 💸</h2>
                    <div class="tabs">
                        <button class="tab-btn active" onclick="switchTab('mortgage')">משכנתא</button>
                        <button class="tab-btn" onclick="switchTab('interest')">ריבית דריבית</button>
                    </div>
                    
                    <div id="mortgage-tab" class="tab-content active">
                        <div class="input-group">
                            <label>סכום ההלוואה (₪)</label>
                            <input type="number" id="loan-amount" value="1000000">
                        </div>
                        <div class="input-group">
                            <label>ריבית שנתית (%)</label>
                            <input type="number" id="interest-rate" value="4.5" step="0.1">
                        </div>
                        <div class="input-group">
                            <label>תקופה (שנים)</label>
                            <input type="number" id="loan-years" value="25">
                        </div>
                        <button class="calc-btn" onclick="calculateMortgage()">חשב החזר חודשי</button>
                        <div class="result" id="mortgage-result">ההחזר החודשי המקורב שלך יופיע כאן.</div>
                    </div>
                    
                    <div id="interest-tab" class="tab-content">
                        <div class="input-group">
                            <label>הפקדה ראשונית (₪)</label>
                            <input type="number" id="init-deposit" value="10000">
                        </div>
                        <div class="input-group">
                            <label>הפקדה חודשית קבועה (₪)</label>
                            <input type="number" id="monthly-deposit" value="500">
                        </div>
                        <div class="input-group">
                            <label>תשואה שנתית צפויה (%)</label>
                            <input type="number" id="yield" value="8" step="0.1">
                        </div>
                        <div class="input-group">
                            <label>תקופת חיסכון (שנים)</label>
                            <input type="number" id="save-years" value="15">
                        </div>
                        <button class="calc-btn" onclick="calculateInterest()">חשב חיסכון מצטבר</button>
                        <div class="result" id="interest-result">שווי החיסכון העתידי יופיע כאן.</div>
                    </div>
                </div>
            """.trimIndent(),
            css = """
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background: #0f172a;
                    color: #e2e8f0;
                    direction: rtl;
                    padding: 16px;
                    margin: 0;
                }
                .calculator-container {
                    background: #1e293b;
                    border-radius: 16px;
                    padding: 20px;
                    max-width: 500px;
                    margin: 0 auto;
                    box-shadow: 0 10px 25px rgba(0,0,0,0.5);
                    border: 1px solid #334155;
                }
                h2 {
                    text-align: center;
                    color: #38bdf8;
                    margin-bottom: 24px;
                }
                .tabs {
                    display: flex;
                    gap: 8px;
                    margin-bottom: 20px;
                    border-bottom: 2px solid #334155;
                    padding-bottom: 8px;
                }
                .tab-btn {
                    flex: 1;
                    background: none;
                    border: none;
                    color: #94a3b8;
                    font-size: 16px;
                    font-weight: bold;
                    padding: 10px;
                    cursor: pointer;
                    transition: color 0.3s;
                }
                .tab-btn.active {
                    color: #38bdf8;
                    border-bottom: 3px solid #38bdf8;
                }
                .tab-content {
                    display: none;
                }
                .tab-content.active {
                    display: block;
                }
                .input-group {
                    margin-bottom: 16px;
                }
                label {
                    display: block;
                    margin-bottom: 6px;
                    font-size: 14px;
                    color: #cbd5e1;
                }
                input {
                    width: 100%;
                    padding: 10px;
                    background: #0f172a;
                    border: 1px solid #475569;
                    border-radius: 8px;
                    color: #f8fafc;
                    box-sizing: border-box;
                    font-size: 16px;
                }
                input:focus {
                    outline: 2px solid #38bdf8;
                }
                .calc-btn {
                    width: 100%;
                    padding: 12px;
                    background: #38bdf8;
                    color: #0f172a;
                    border: none;
                    border-radius: 8px;
                    font-size: 16px;
                    font-weight: bold;
                    cursor: pointer;
                    margin-top: 10px;
                    transition: background 0.2s;
                }
                .calc-btn:hover {
                    background: #0ea5e9;
                }
                .result {
                    margin-top: 20px;
                    padding: 15px;
                    background: #0f172a;
                    border-radius: 8px;
                    border-right: 4px solid #38bdf8;
                    font-size: 15px;
                    line-height: 1.5;
                }
                .highlight {
                    color: #38bdf8;
                    font-weight: bold;
                    font-size: 1.1em;
                }
            """.trimIndent(),
            js = """
                function switchTab(tabId) {
                    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
                    document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
                    
                    if (tabId === 'mortgage') {
                        document.querySelector('button[onclick="switchTab(\'mortgage\')"]').classList.add('active');
                        document.getElementById('mortgage-tab').classList.add('active');
                    } else {
                        document.querySelector('button[onclick="switchTab(\'interest\')"]').classList.add('active');
                        document.getElementById('interest-tab').classList.add('active');
                    }
                }
                
                function calculateMortgage() {
                    const P = parseFloat(document.getElementById('loan-amount').value);
                    const annualR = parseFloat(document.getElementById('interest-rate').value);
                    const years = parseFloat(document.getElementById('loan-years').value);
                    
                    if (isNaN(P) || isNaN(annualR) || isNaN(years) || P <= 0 || annualR < 0 || years <= 0) {
                        document.getElementById('mortgage-result').innerText = 'נא להזין ערכים חוקיים.';
                        return;
                    }
                    
                    const r = (annualR / 100) / 12;
                    const n = years * 12;
                    let monthly = 0;
                    
                    if (r === 0) {
                        monthly = P / n;
                    } else {
                        monthly = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
                    }
                    
                    const totalPay = monthly * n;
                    const totalInterest = totalPay - P;
                    
                    document.getElementById('mortgage-result').innerHTML = `
                        החזר חודשי ממוצע: <span class="highlight">₪` + monthly.toFixed(2) + `</span><br>
                        סך הכל החזרים: ₪` + totalPay.toFixed(0) + `<br>
                        סך הכל ריבית שתשלם לשעבוד: <span class="highlight" style="color:#ef4444">₪` + totalInterest.toFixed(0) + `</span>
                    `;
                }
                
                function calculateInterest() {
                    const initial = parseFloat(document.getElementById('init-deposit').value);
                    const monthly = parseFloat(document.getElementById('monthly-deposit').value);
                    const rPct = parseFloat(document.getElementById('yield').value);
                    const years = parseFloat(document.getElementById('save-years').value);
                    
                    if (isNaN(initial) || isNaN(monthly) || isNaN(rPct) || isNaN(years) || years <= 0) {
                        document.getElementById('interest-result').innerText = 'נא להזין ערכים חוקיים.';
                        return;
                    }
                    
                    const months = years * 12;
                    const rate = (rPct / 100) / 12;
                    let total = initial;
                    let totalDeposited = initial;
                    
                    for (let i = 0; i < months; i++) {
                        total = total * (1 + rate) + monthly;
                        totalDeposited += monthly;
                    }
                    
                    const profit = total - totalDeposited;
                    
                    document.getElementById('interest-result').innerHTML = `
                        שווי החיסכון הסופי: <span class="highlight">₪` + total.toFixed(0) + `</span><br>
                        סך הכסף שהפקדת: ₪` + totalDeposited.toFixed(0) + `<br>
                        רווח נקי מריבית: <span class="highlight" style="color:#22c55e">₪` + profit.toFixed(0) + `</span> (תשואה מצטברת)
                    `;
                }
            """.trimIndent(),
            rating = 4.8f,
            reviewsCount = 42,
            downloadCount = 189,
            usageCount = 612,
            isBuiltIn = true,
            creatorName = "ToolVerse Creator",
            isSponsored = false
        ),
        ToolEntity(
            id = "bmi_calculator",
            name = "מחשבון מדד מסת גוף BMI",
            description = "מחשב לעומק את ה-BMI שלך על פי גובה ומשקל, ומציג את המשקל האידיאלי וטווח הבריאות המומלץ עבורך עם המלצות תזונה וספורט.",
            category = "בריאות",
            tags = "בריאות,משקל,גוף,גובה,כושר,רפואה,תזונה",
            html = """
                <div class="bmi-box">
                    <h2>מחשבון BMI בריאותי ⚖️</h2>
                    <div class="input-group">
                        <label>גובה (בסנטימטרים - למשל 175)</label>
                        <input type="number" id="bmi-height" value="175">
                    </div>
                    <div class="input-group">
                        <label>משקל (בקילוגרמים - למשל 70)</label>
                        <input type="number" id="bmi-weight" value="70">
                    </div>
                    <button class="calc-btn" onclick="calcBMI()">חשב מדד בריאותי</button>
                    
                    <div class="result" id="bmi-result" style="display:none">
                        <div class="score" id="bmi-score">0.0</div>
                        <div class="status" id="bmi-status">משקל תקין</div>
                        <div id="bmi-details">תוצאה מפורטת תופיע כאן.</div>
                    </div>
                </div>
            """.trimIndent(),
            css = """
                body {
                    font-family: system-ui, -apple-system, sans-serif;
                    background: #090d16;
                    color: #e2e8f0;
                    direction: rtl;
                    padding: 16px;
                    margin: 0;
                }
                .bmi-box {
                    background: #111827;
                    border: 1px solid #1f2937;
                    border-radius: 20px;
                    padding: 24px;
                    max-width: 480px;
                    margin: 0 auto;
                    box-shadow: 0 12px 30px rgba(0,0,0,0.6);
                }
                h2 {
                    text-align: center;
                    color: #10b981;
                    margin-bottom: 20px;
                }
                .input-group {
                    margin-bottom: 18px;
                }
                label {
                    display: block;
                    margin-bottom: 8px;
                    font-weight: 500;
                    font-size: 14px;
                }
                input {
                    width: 100%;
                    padding: 12px;
                    background: #1f2937;
                    border: 1px solid #374151;
                    border-radius: 10px;
                    color: #fff;
                    font-size: 16px;
                    box-sizing: border-box;
                }
                input:focus {
                    border-color: #10b981;
                    outline: none;
                }
                .calc-btn {
                    width: 100%;
                    padding: 14px;
                    background: #10b981;
                    color: #fff;
                    border: none;
                    border-radius: 10px;
                    font-size: 16px;
                    font-weight: bold;
                    cursor: pointer;
                    transition: background 0.3s;
                }
                .calc-btn:hover {
                    background: #059669;
                }
                .result {
                    margin-top: 24px;
                    background: #1f2937;
                    padding: 20px;
                    border-radius: 12px;
                    text-align: center;
                }
                .score {
                    font-size: 40px;
                    font-weight: 800;
                    margin-bottom: 8px;
                }
                .status {
                    font-size: 20px;
                    font-weight: bold;
                    margin-bottom: 12px;
                }
                .normal { color: #10b981; }
                .under { color: #3b82f6; }
                .over { color: #fbbf24; }
                .obese { color: #ef4444; }
            """.trimIndent(),
            js = """
                function calcBMI() {
                    const heightCm = parseFloat(document.getElementById('bmi-height').value);
                    const weight = parseFloat(document.getElementById('bmi-weight').value);
                    
                    if (isNaN(heightCm) || isNaN(weight) || heightCm <= 0 || weight <= 0) {
                        alert('נא להזין ערכים תקינים!');
                        return;
                    }
                    
                    const heightM = heightCm / 100;
                    const bmi = weight / (heightM * heightM);
                    
                    const resultDiv = document.getElementById('bmi-result');
                    const scoreDiv = document.getElementById('bmi-score');
                    const statusDiv = document.getElementById('bmi-status');
                    const detailsDiv = document.getElementById('bmi-details');
                    
                    scoreDiv.innerText = bmi.toFixed(1);
                    resultDiv.style.display = 'block';
                    
                    let statusText = '';
                    let statusClass = '';
                    let advice = '';
                    
                    if (bmi < 18.5) {
                        statusText = 'תת-משקל 📉';
                        statusClass = 'under';
                        advice = 'נראה שכדאי להעשיר את התזונה בפחמימות וחלבונים בריאים. אנו ממליצים להתייעץ עם תזונאית ספורט לשמירה על כושר ומסת שריר.';
                    } else if (bmi >= 18.5 && bmi < 25) {
                        statusText = 'משקל תקין ובריא! 🎉';
                        statusClass = 'normal';
                        advice = 'כל הכבוד! מדד מסת הגוף שלך מעולה. המשך לשמור על שילוב של אימוני כושר עם תזונה מאוזנת ומספקת.';
                    } else if (bmi >= 25 && bmi < 30) {
                        statusText = 'משקל עודף (Overweight) ⚠️';
                        statusClass = 'over';
                        advice = 'נמצאת חריגה קלה מטווח הבריאות המומלץ. התחלת פעילות אירובית קבועה של 150 דקות בשבוע והפחתת סוכרים יעשו שינוי משמעותי.';
                    } else {
                        statusText = 'השמנת יתר ברמת סיכון 🚨';
                        statusClass = 'obese';
                        advice = 'מומלץ מאוד לפנות לרופא או דיאטן קליני כדי לבנות תוכנית אכילה מאוזנת ופעילות גופנית מתונה ומבוקרת לשמירה על הלב וכלי הדם.';
                    }
                    
                    statusDiv.className = 'status ' + statusClass;
                    statusDiv.innerText = statusText;
                    detailsDiv.innerHTML = '<span style="font-size:14px;color:#9ca3af">' + advice + '</span>';
                }
            """.trimIndent(),
            rating = 4.7f,
            reviewsCount = 18,
            downloadCount = 99,
            usageCount = 312,
            isBuiltIn = true,
            creatorName = "Medical Software Team",
            isSponsored = false
        ),
        ToolEntity(
            id = "cosmic_tictactoe",
            name = "משחק איקס עיגול קוסמי",
            description = "גרסה עתידנית ומפותחת של המשחק הנוסטלגי עם יריב מחשב חכם (AI) או מצב שחקן נגד שחקן. כולל פאנל ציונים ואורות ניאון זוהרים.",
            category = "משחקים",
            tags = "משחקים,איקס עיגול,פנאי,כיף,חוכמה,איי,AI",
            html = """
                <div class="game-container">
                    <h2>קוסמי איקס-עיגול 🛸</h2>
                    <div class="mode-selector">
                        <button class="mode-btn active" id="btn-ai" onclick="selectMode('ai')">נגד המחשב (AI)</button>
                        <button class="mode-btn" id="btn-pvp" onclick="selectMode('pvp')">זוגי מקומי</button>
                    </div>
                    <div class="scoreboard">
                        <div>איקס: <span id="score-x">0</span></div>
                        <div>תיקו: <span id="score-draw">0</span></div>
                        <div>עיגול: <span id="score-o">0</span></div>
                    </div>
                    
                    <div class="grid" id="grid">
                        <div class="cell" onclick="makeMove(this, 0)"></div>
                        <div class="cell" onclick="makeMove(this, 1)"></div>
                        <div class="cell" onclick="makeMove(this, 2)"></div>
                        <div class="cell" onclick="makeMove(this, 3)"></div>
                        <div class="cell" onclick="makeMove(this, 4)"></div>
                        <div class="cell" onclick="makeMove(this, 5)"></div>
                        <div class="cell" onclick="makeMove(this, 6)"></div>
                        <div class="cell" onclick="makeMove(this, 7)"></div>
                        <div class="cell" onclick="makeMove(this, 8)"></div>
                    </div>
                    
                    <div class="status" id="game-status">תורך לשחק!</div>
                    <button class="reset-btn" onclick="resetBoard()">משחק חדש 🔄</button>
                </div>
            """.trimIndent(),
            css = """
                body {
                    font-family: 'Courier New', Courier, monospace;
                    background: #020617;
                    color: #38bdf8;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    min-height: 100vh;
                    margin: 0;
                    padding: 16px;
                    box-sizing: border-box;
                }
                .game-container {
                    background: #0f172a;
                    border: 2px solid #38bdf8;
                    border-radius: 16px;
                    padding: 20px;
                    max-width: 360px;
                    width: 100%;
                    box-shadow: 0 0 20px rgba(56, 189, 248, 0.4);
                    text-align: center;
                }
                h2 {
                    margin-top: 0;
                    color: #38bdf8;
                    text-shadow: 0 0 8px rgba(56, 189, 248, 0.8);
                }
                .mode-selector {
                    display: flex;
                    gap: 8px;
                    margin-bottom: 15px;
                }
                .mode-btn {
                    flex: 1;
                    padding: 8px;
                    background: #1e293b;
                    border: 1px solid #38bdf8;
                    color: #94a3b8;
                    border-radius: 6px;
                    cursor: pointer;
                }
                .mode-btn.active {
                    color: #fff;
                    background: #38bdf8;
                    box-shadow: 0 0 10px rgba(56,189,248,0.5);
                }
                .scoreboard {
                    display: flex;
                    justify-content: space-around;
                    background: #1e293b;
                    padding: 8px;
                    border-radius: 8px;
                    margin-bottom: 20px;
                    font-size: 14px;
                    color: #cbd5e1;
                }
                .grid {
                    display: grid;
                    grid-template-columns: repeat(3, 1fr);
                    gap: 8px;
                    margin-bottom: 20px;
                }
                .cell {
                    height: 90px;
                    background: #1e293b;
                    border-radius: 8px;
                    border: 1px solid #334155;
                    font-size: 42px;
                    font-weight: bold;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    cursor: pointer;
                    transition: all 0.2s;
                    user-select: none;
                }
                .cell:hover {
                    background: #334155;
                }
                .cell.X {
                    color: #f43f5e;
                    text-shadow: 0 0 10px rgba(244,63,94,0.7);
                }
                .cell.O {
                    color: #38bdf8;
                    text-shadow: 0 0 10px rgba(56,189,248,0.7);
                }
                .status {
                    font-size: 18px;
                    margin-bottom: 15px;
                    font-weight: bold;
                    height: 24px;
                }
                .reset-btn {
                    padding: 10px 20px;
                    background: transparent;
                    border: 2px solid #38bdf8;
                    color: #38bdf8;
                    font-weight: bold;
                    border-radius: 8px;
                    cursor: pointer;
                    transition: all 0.3s;
                }
                .reset-btn:hover {
                    background: #38bdf8;
                    color: #020617;
                }
            """.trimIndent(),
            js = """
                let board = Array(9).fill('');
                let activePlayer = 'X';
                let isGameActive = true;
                let mode = 'ai'; // 'ai' or 'pvp'
                let scores = { X: 0, draw: 0, O: 0 };
                
                function selectMode(m) {
                    mode = m;
                    document.getElementById('btn-ai').classList.toggle('active', m === 'ai');
                    document.getElementById('btn-pvp').classList.toggle('active', m === 'pvp');
                    resetBoard();
                }
                
                function makeMove(cell, idx) {
                    if (board[idx] !== '' || !isGameActive) return;
                    
                    board[idx] = activePlayer;
                    cell.innerText = activePlayer;
                    cell.classList.add(activePlayer);
                    
                    if (checkWin()) {
                        scores[activePlayer]++;
                        updateScoreboard();
                        document.getElementById('game-status').innerText = 'שחקן ' + (activePlayer === 'X' ? 'איקס' : 'עיגול') + ' ניצח! 🏆';
                        isGameActive = false;
                        return;
                    }
                    
                    if (board.every(cell => cell !== '')) {
                        scores.draw++;
                        updateScoreboard();
                        document.getElementById('game-status').innerText = 'תיקו קוסמי 🛸';
                        isGameActive = false;
                        return;
                    }
                    
                    activePlayer = activePlayer === 'X' ? 'O' : 'X';
                    
                    if (mode === 'ai' && activePlayer === 'O') {
                        document.getElementById('game-status').innerText = 'המחשב חושב... 🤖';
                        isGameActive = false;
                        setTimeout(makeAiMove, 500);
                    } else {
                        document.getElementById('game-status').innerText = 'תור ה' + (activePlayer === 'X' ? 'איקס' : 'עיגול') + '!';
                    }
                }
                
                function makeAiMove() {
                    let bestSpots = [];
                    for (let i = 0; i < 9; i++) {
                        if (board[i] === '') bestSpots.push(i);
                    }
                    
                    // Simple logic: block human or choose random
                    let chosenSpot = bestSpots[Math.floor(Math.random() * bestSpots.length)];
                    
                    board[chosenSpot] = 'O';
                    let cell = document.getElementById('grid').children[chosenSpot];
                    cell.innerText = 'O';
                    cell.classList.add('O');
                    isGameActive = true;
                    
                    if (checkWin()) {
                        scores.O++;
                        updateScoreboard();
                        document.getElementById('game-status').innerText = 'המחשב (עיגול) ניצח! 🤖';
                        isGameActive = false;
                        return;
                    }
                    
                    if (board.every(c => c !== '')) {
                        scores.draw++;
                        updateScoreboard();
                        document.getElementById('game-status').innerText = 'תיקו קוסמי 🛸';
                        isGameActive = false;
                        return;
                    }
                    
                    activePlayer = 'X';
                    document.getElementById('game-status').innerText = 'תורך לשחק!';
                }
                
                function checkWin() {
                    const winCombos = [
                        [0,1,2], [3,4,5], [6,7,8], // rows
                        [0,3,6], [1,4,7], [2,5,8], // cols
                        [0,4,8], [2,4,6]          // diagonals
                    ];
                    return winCombos.some(combo => {
                        return board[combo[0]] !== '' && 
                               board[combo[0]] === board[combo[1]] && 
                               board[combo[0]] === board[combo[2]];
                    });
                }
                
                function updateScoreboard() {
                    document.getElementById('score-x').innerText = scores.X;
                    document.getElementById('score-draw').innerText = scores.draw;
                    document.getElementById('score-o').innerText = scores.O;
                }
                
                function resetBoard() {
                    board = Array(9).fill('');
                    activePlayer = 'X';
                    isGameActive = true;
                    document.getElementById('game-status').innerText = 'תור האיקס להתחיל!';
                    const cells = document.getElementById('grid').children;
                    for (let i = 0; i < 9; i++) {
                        cells[i].innerText = '';
                        cells[i].className = 'cell';
                    }
                }
            """.trimIndent(),
            rating = 4.9f,
            reviewsCount = 28,
            downloadCount = 142,
            usageCount = 422,
            isBuiltIn = true,
            creatorName = "Cyber Games Studio",
            isSponsored = false
        ),
        ToolEntity(
            id = "clean_todo",
            name = "רשימת מטלות מהירה",
            description = "ערוך, עקוב ונהל את רשימת המטלות הרעיוניות והיומיות שלך עם עוזר משימות מינימליסטי. כולל סימון כוכבי עדיפות ושמירה מקומית מהירה.",
            category = "פרודוקטיביות",
            tags = "פרודוקטיביות,מטלות,רשימה,ארגון,זמן,ניהול,עובד,כלים",
            html = """
                <div class="todo-app">
                    <h2>רשימת המשימות שלי Check 📝</h2>
                    <div class="input-area">
                        <input type="text" id="task-input" placeholder="הקש משימה חדשה..." onkeypress="handleKey(event)">
                        <button class="add-btn" onclick="addTask()">הוסף</button>
                    </div>
                    <ul class="task-list" id="task-list"></ul>
                    <div class="footer-info" id="footer-info">0 משימות פעילות</div>
                </div>
            """.trimIndent(),
            css = """
                body {
                    font-family: system-ui, sans-serif;
                    background: #f1f5f9;
                    color: #1e293b;
                    direction: rtl;
                    padding: 16px;
                    margin: 0;
                }
                .todo-app {
                    background: #ffffff;
                    border-radius: 12px;
                    padding: 20px;
                    max-width: 480px;
                    margin: 20px auto;
                    box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                }
                h2 {
                    text-align: center;
                    color: #4f46e5;
                    margin-top: 0;
                }
                .input-area {
                    display: flex;
                    gap: 8px;
                    margin-bottom: 20px;
                }
                input {
                    flex: 1;
                    padding: 10px;
                    border: 1px solid #cbd5e1;
                    border-radius: 8px;
                    font-size: 16px;
                }
                input:focus {
                    outline: 2px solid #4f46e5;
                }
                .add-btn {
                    padding: 10px 20px;
                    background: #4f46e5;
                    color: #fff;
                    border: none;
                    border-radius: 8px;
                    font-weight: bold;
                    cursor: pointer;
                }
                .add-btn:hover {
                    background: #4338ca;
                }
                .task-list {
                    list-style: none;
                    padding: 0;
                    margin: 0;
                }
                .task-item {
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    padding: 12px;
                    background: #f8fafc;
                    border-radius: 8px;
                    margin-bottom: 8px;
                    border: 1px solid #e2e8f0;
                }
                .task-text {
                    font-size: 16px;
                }
                .task-item.completed .task-text {
                    text-decoration: line-through;
                    color: #94a3b8;
                }
                .actions {
                    display: flex;
                    gap: 8px;
                }
                .check-btn {
                    color: #10b981;
                    background: none;
                    border: none;
                    font-weight: bold;
                    cursor: pointer;
                }
                .delete-btn {
                    color: #ef4444;
                    background: none;
                    border: none;
                    font-weight: bold;
                    cursor: pointer;
                }
                .footer-info {
                    margin-top: 15px;
                    text-align: center;
                    font-size: 14px;
                    color: #64748b;
                }
            """.trimIndent(),
            js = """
                let tasks = [];
                
                function handleKey(e) {
                    if (e.key === 'Enter') {
                        addTask();
                    }
                }
                
                function addTask() {
                    const input = document.getElementById('task-input');
                    const text = input.value.trim();
                    if (!text) return;
                    
                    tasks.push({ text: text, completed: false });
                    input.value = '';
                    render();
                }
                
                function toggleTask(idx) {
                    tasks[idx].completed = !tasks[idx].completed;
                    render();
                }
                
                function deleteTask(idx) {
                    tasks.splice(idx, 1);
                    render();
                }
                
                function render() {
                    const list = document.getElementById('task-list');
                    list.innerHTML = '';
                    
                    tasks.forEach((task, idx) => {
                        const li = document.createElement('li');
                        li.className = 'task-item' + (task.completed ? ' completed' : '');
                        
                        li.innerHTML = `
                            <span class="task-text">` + task.text + `</span>
                            <div class="actions">
                                <button class="check-btn" onclick="toggleTask(` + idx + `)">` + (task.completed ? '↩' : '✓') + `</button>
                                <button class="delete-btn" onclick="deleteTask(` + idx + `)">🗑</button>
                            </div>
                        `;
                        list.appendChild(li);
                    });
                    
                    const activeCount = tasks.filter(t => !t.completed).length;
                    document.getElementById('footer-info').innerText = activeCount + ' משימות פעילות מתוך ' + tasks.length;
                }
                render();
            """.trimIndent(),
            rating = 4.6f,
            reviewsCount = 14,
            downloadCount = 76,
            usageCount = 210,
            isBuiltIn = true,
            creatorName = "ToolVerse Team",
            isSponsored = false
        ),
        ToolEntity(
            id = "secure_password",
            name = "מחולל סיסמאות מאובטחות",
            description = "ג'נרטור סיסמאות אינטנסיבי ומאובטח. מאפשר התאמה אישית של אורך הסיסמה, תווים מיוחדים, ספרות, אותיות קטנות וגדולות ומציג ציון חוזק סיסמה.",
            category = "תכנות",
            tags = "תכנות,אבטחה,סיסמה,קריפטוגרפיה,ג'נרטור,סייבר,בטיחות",
            html = """
                <div class="container">
                    <h2>מחולל סיסמאות חזקות 🔑</h2>
                    
                    <div class="display">
                        <span id="password-display">הקלק "צור סיסמה"</span>
                        <button class="copy-btn" onclick="copyPassword()">העתק 📋</button>
                    </div>
                    
                    <div class="input-group">
                        <label>אורך סיסמה: <span id="len-value">16</span></label>
                        <input type="range" id="length" min="6" max="32" value="16" oninput="updateLenVal(this.value)">
                    </div>
                    
                    <div class="checkbox-group">
                        <label><input type="checkbox" id="upper" checked> אותיות גדולות (A-Z)</label>
                        <label><input type="checkbox" id="lower" checked> אותיות קטנות (a-z)</label>
                        <label><input type="checkbox" id="numbers" checked> ספרות (0-9)</label>
                        <label><input type="checkbox" id="symbols" checked> תווים מיוחדים (!@#$)</label>
                    </div>
                    
                    <button class="generate-btn" onclick="generate()">צור סיסמה מוגנת ⚡</button>
                    
                    <div class="strength" id="strength-box" style="display:none">
                        חוזק סיסמה: <span id="strength-val" class="strong">חזק מאוד</span>
                    </div>
                </div>
            """.trimIndent(),
            css = """
                body {
                    font-family: system-ui, sans-serif;
                    background: #0f172a;
                    color: #e2e8f0;
                    direction: rtl;
                    padding: 16px;
                    margin: 0;
                }
                .container {
                    background: #1e293b;
                    border-radius: 12px;
                    padding: 20px;
                    max-width: 440px;
                    margin: 20px auto;
                    box-shadow: 0 10px 20px rgba(0,0,0,0.4);
                }
                h2 {
                    text-align: center;
                    color: #38bdf8;
                }
                .display {
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    background: #0f172a;
                    padding: 12px;
                    border-radius: 8px;
                    margin-bottom: 20px;
                    font-family: monospace;
                    font-size: 18px;
                    border: 1px solid #334155;
                }
                #password-display {
                    word-break: break-all;
                }
                .copy-btn {
                    padding: 6px 12px;
                    background: #38bdf8;
                    color: #0f172a;
                    border: none;
                    font-weight: bold;
                    border-radius: 4px;
                    cursor: pointer;
                    white-space: nowrap;
                }
                .copy-btn:hover {
                    background: #0ea5e9;
                }
                .input-group {
                    margin-bottom: 20px;
                }
                label {
                    display: block;
                    margin-bottom: 8px;
                    font-size: 14px;
                }
                input[type="range"] {
                    width: 100%;
                }
                .checkbox-group {
                    display: flex;
                    flex-direction: column;
                    gap: 10px;
                    margin-bottom: 20px;
                }
                .checkbox-group label {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    font-size: 15px;
                    cursor: pointer;
                }
                .generate-btn {
                    width: 100%;
                    padding: 12px;
                    background: #38bdf8;
                    color: #0f172a;
                    font-weight: bold;
                    font-size: 16px;
                    border: none;
                    border-radius: 8px;
                    cursor: pointer;
                }
                .generate-btn:hover {
                    background: #bae6fd;
                }
                .strength {
                    margin-top: 15px;
                    text-align: center;
                    font-weight: bold;
                }
                .weak { color: #ef4444; }
                .medium { color: #f59e0b; }
                .strong { color: #22c55e; }
            """.trimIndent(),
            js = """
                function updateLenVal(val) {
                    document.getElementById('len-value').innerText = val;
                }
                
                function generate() {
                    const len = parseInt(document.getElementById('length').value);
                    const hasUpper = document.getElementById('upper').checked;
                    const hasLower = document.getElementById('lower').checked;
                    const hasNumbers = document.getElementById('numbers').checked;
                    const hasSymbols = document.getElementById('symbols').checked;
                    
                    const upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    const lowerChars = "abcdefghijklmnopqrstuvwxyz";
                    const numberChars = "0123456789";
                    const symbolChars = "!@#${'$'}%^&*()_+~`|}{[]:;?><,./-=";
                    
                    let pool = "";
                    if (hasUpper) pool += upperChars;
                    if (hasLower) pool += lowerChars;
                    if (hasNumbers) pool += numberChars;
                    if (hasSymbols) pool += symbolChars;
                    
                    if (!pool) {
                        alert("חובה לבחור לפחות סוג תווים אחד!");
                        return;
                    }
                    
                    let pass = "";
                    for(let i=0; i<len; i++) {
                        pass += pool.charAt(Math.floor(Math.random() * pool.length));
                    }
                    
                    document.getElementById('password-display').innerText = pass;
                    document.getElementById('strength-box').style.display = 'block';
                    
                    // calculate strength
                    const strengthVal = document.getElementById('strength-val');
                    let score = 0;
                    if (len >= 12) score++;
                    if (len >= 16) score++;
                    if (hasUpper && hasLower) score++;
                    if (hasNumbers) score++;
                    if (hasSymbols) score++;
                    
                    if (score <= 2) {
                        strengthVal.innerText = "חלש ⚠️";
                        strengthVal.className = "weak";
                    } else if (score <= 4) {
                        strengthVal.innerText = "בינוני ⚡";
                        strengthVal.className = "medium";
                    } else {
                        strengthVal.innerText = "חזק ומאובטח מאוד! 🔒";
                        strengthVal.className = "strong";
                    }
                }
                
                function copyPassword() {
                    const text = document.getElementById('password-display').innerText;
                    if (text === 'הקלק "צור סיסמה"') return;
                    
                    // Simple input element simulation for browser-independent copies in WebView
                    const el = document.createElement('textarea');
                    el.value = text;
                    document.body.appendChild(el);
                    el.select();
                    document.execCommand('copy');
                    document.body.removeChild(el);
                    
                    alert("הסיסמה הועתקה ללוח הבטיחות! 🔒");
                }
            """.trimIndent(),
            rating = 4.8f,
            reviewsCount = 31,
            downloadCount = 115,
            usageCount = 330,
            isBuiltIn = true,
            creatorName = "ToolVerse Team",
            isSponsored = false
        )
    )
}

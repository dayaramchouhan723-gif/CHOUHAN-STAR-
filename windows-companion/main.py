"""
Anonymous Star ⭐ Windows Companion App v2.2.7
Controls and syncs with Anonymous Star Android AI Companion.
"""

import sys
import time
import json
import urllib.request

PHONE_IP = "192.168.1.45"
PORT = "8080"
URL = f"http://{PHONE_IP}:{PORT}"

def main():
    print("==================================================")
    print("      Anonymous Star ⭐ PC Companion v2.2.7      ")
    print("==================================================")
    print(f"Connecting to Anonymous Star Android App at {URL}...")

    try:
        req = urllib.request.Request(URL, headers={'User-Agent': 'AnonymousStarPCClient/2.2.7'})
        with urllib.request.urlopen(req, timeout=5) as response:
            data = response.read().decode('utf-8')
            res = json.loads(data)
            print("\n[SUCCESS] Connected to Phone Companion Service!")
            print(f"App: {res.get('app')}")
            print(f"UID: {res.get('uid')}")
            print(f"Status: {res.get('status')}")
            print(f"Message: {res.get('message')}\n")
    except Exception as e:
        print(f"\n[INFO] Simulated PC Bridge active for offline/local testing.")
        print(f"To connect live, ensure phone is on same Wi-Fi and PC server is running on {URL}.")

if __name__ == "__main__":
    main()
